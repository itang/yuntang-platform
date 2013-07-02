(ns yuntang.user.ui.handlers
  (:require [clojure.string :refer [trim]]
            [clojure.tools.logging :as log]
            [cljtang.core :refer :all]
            [cljtang.util :refer :all]
            [ring.util.response :refer [not-found]]
            [compojure.core :refer :all]
            [noir.response :refer [redirect]]
            [noir.session :as session]
            [metis.core :refer :all]
            [cheshire.core :as cheshire]
            [clj-pretty-format.core :refer [pretty-format]]
            [clj-captcha.core :refer [captcha-response-correc?]]
            [cljwtang.core :refer :all]
            [cljwtang.inject :refer [fn-app-config]]
            [cljwtang.view :refer :all]
            [cljwtang.utils.mail :refer :all]
            [yuntang.user.core :refer :all]
            [yuntang.user.ui.util :refer [check-username-pattern]]
            [yuntang.user.ui.config :refer :all]))

(defn- password? [map key options]
  (if-let [password (some-> (get map key) trim)]
    (when (< (count password) 6 )
      "密码至少6位")
    "密码为空"))

(defn- username? [map key options]
  (if-let [username (some-> (get map key) trim)]
    (let [check-result (check-username-pattern username)]
      (cond
        (not-nil? check-result) check-result
        (exists-user? username) (str username "已经被注册")))
    "用户名为空"))

(defn- email? [map key options]
  (if-let [email (some-> (get map key) trim)]
    (cond
      (exists-user-by-email? email) (str email "已经被注册")
      :else nil)
    "email为空"))

(defvalidator signin-form-validator
  [[:username :password] :presence {:message "This field is required."}]
  [:password :password?])

(defn- redirect-signin-page [& [active-form]]
  (redirect
    (when-> "/signin" active-form #(str % "?active-form=" active-form))))

;; 登录页面
(defhandler signin-page [active-form req]
  (let [postback-params (postback-params)]
    (layout-base-view "signin"
      {:captcha-enabled? (captcha-enabled?)
       :ori-active-form active-form
       :active-form (case active-form 
                          "second" "eq(1)"
                          "three" "eq(2)"
                          nil)
       :params postback-params
       :params-json (cheshire/generate-string postback-params)})))

(defn- check-user-status [user]
  (cond
    (not (:enabled user))  {:username ["帐户未激活或帐户未启用"]}
    (not (:account_non_expired user))  {:username ["帐户已经过期"]}
    (not (:credentials_non_expired user))  {:username ["帐户认证信息已经过期"]}
    (not (:account_non_locked  user))  {:username ["帐户已经被锁定"]}))

;; 登录
(defhandler-with-validates signin [username password req]
  [signin-form-validator
   (fn-* (when (and (captcha-enabled?)
                 (not (captcha-response-correc?)))
           {:captcha ["验证码填写有误"]}))
   (fn-*
     (if-let [user (check-user username password)]
       (check-user-status user)
       {:username ["用户不存在或者密码不正确"]}))]
  :success
  (let [user (find-user-in-uid-username-email-for-session username)]
    (log/info username "登录成功!")
    (set-current-user! user)
    (redirect "/"))
  :failture
  (do
    (log/warn username "登录失败!")
    (redirect-signin-page)))

;; 注销
(defhandler logout []
  (session/remove! :user)
  (redirect-signin-page))

(defvalidator signup-form-validator
  [:username [:presence {:message "Please input your username."}
              :length {:less-than-or-equal-to 30
                       :greater-than-or-equal-to 5
                       :message "用户名长度5-20"}]]
  [:email [:presence {:message "Please input your email."}
           :email {:message "邮件格式不对"}]]
  [:password [:presence {:message "Please input your password."}
              :length {:less-than-or-equal-to 20
                       :greater-than-or-equal-to 6
                       :message "密码长度6-20"}]]
  [:password :password?]
  [:username :username?]
  [:email :email?])

;; 注册
(defhandler-with-validates signup [username email password realname type req]
  [signup-form-validator]
  :success 
  (let [email (trim email)
        activation-mode (user-regist-activation-mode)
        activation-mode-by-email (= :by-email activation-mode)
        activation-mode-auto (= :auto activation-mode)
        activation-mode-by-manual (= :by-manual activation-mode)
        activation-code (uuid2)]
    (create-user! {:username (trim username)
                   :email email
                   :password (trim password)
                   :realname (trim realname)
                   :type (trim type)
                   :enabled activation-mode-auto
                   :activation_code activation-code
                   :activation_code_created_at (moment)})
    (when activation-mode-by-email ;; 邮件激活模式
      (let [activation-url
            (str (activation-url-prefix) "?code=" activation-code)]
        (log/info "发送用户注册激活邮件")
        (send-mail-by-template
          {:to email :subject (str "欢迎注册wapp(wapp.com)，请激活你的帐号")}
          "mail-activation-template"
          {:activation-url activation-url
           :email email})))
    (layout-base-view "signup-submit-finish" 
                      {:email email
                       :mail-vendor (mail-vendor-by-email-account email)
                       :by-email activation-mode-by-email
                       :auto activation-mode-auto
                       :by-manual activation-mode-by-manual}))
  :failture
  (redirect-signin-page "second"))

;; 用户注册邮件激活
(defhandler activation [code]
  (if-not (= 32 (count code))
    (not-found "")
    (let [user (activation-user! code)]
      (layout-base-view "activation-result" {:user user }))))


(defn- check-email? [map key options]
  (if-let [email (some-> (get map key) trim)]
    (cond
      (not (exists-user-by-email? email)) (str email "不存在")
      :else nil)
    "email为空"))

(defvalidator forget-password-form-validator
  [:email [:presence {:message "Please input your email."}
           :email {:message "邮件格式不对"}]]
  [:email :check-email?])

(defhandler-with-validates forget-password [email]
  [forget-password-form-validator]
  :success
  (let [password-reset-code
        (uuid2)
        reset-url
        (str (reset-password-url-prefix)
             "?email=" email
             "&code=" password-reset-code)]
    (set-password-reset-code! email password-reset-code)
    (send-mail-by-template
          {:to email
           :subject (str "[wapp]-" "申请重置密码邮件")}
          "forget-password-template"
          {:reset-url reset-url})
    (layout-base-view 
      "forget-password-result"
      {:mail-vendor (mail-vendor-by-email-account email)}))
  :failture
  (redirect-signin-page "three"))

(defhandler reset-password [email code]
  (if-not (= 32 (count code))
    (not-found "")
    (let [p (reset-password! email code)
          ctx {:new-password p}]
      (log/debug email)
      (send-mail-by-template
        {:to email :subject (str "[wapp]-" "重置密码邮件通知")}
        "reset-password-notice-template"
        ctx)
      (layout-base-view "reset-password-result" ctx))))

(defhandler settings []
  (redirect "/settings/password"))

(defn- layout-settings-view [template-name ctx & [sctx]]
  (layout-view "layouts/settings"
               (merge {:content (view-template template-name ctx)}
                      sctx)))

(defhandler settings-profile []
  (layout-settings-view "settings/profile" nil {:channel "profile"}))

(defhandler settings-password []
  (layout-settings-view "settings/password" nil {:channel "password"}))

(defvalidator settings-change-password-form-validator
  [[:current_password :password :user_password_confirmation]
   :presence
   {:message "This field is required."}]
  [[:current_password :password :user_password_confirmation] :password?])

(defhandler-with-validates settings-change-password
  [current_password password user_password_confirmation]
  [settings-change-password-form-validator
   (fn-* 
     (cond
       (not (is-current-user-password current_password))
       {:current_password ["当前用户密码不对"]}
       (not= password user_password_confirmation)
       {:password ["两次输入的密码不一致"]}))]
  :success
  (do
    (change-current-user-password! password)
    (flash-msg (success-message "更改用户密码成功!"))
    (redirect "/settings/password"))
  :failture
  (redirect "/settings/password"))

;; 用户管理
(defhandler admin-users []
  (let [today (moment-format "yyyy-MM-dd")
        users (map #(-> %
                      (assoc :created_at_string (format-date (:created_at %)))
                      (assoc :created_at_pretty (pretty-format (:created_at %)))
                      (assoc :type-name (user-types-map (:type %))))
                    (all-users))]
    (layout-view "admin/users" {:users users})))

(defroutes account-routes
  (GET "/signin" [] signin-page)
  (POST "/signin" [] signin)
  (GET "/logout" [] logout)
  (POST "/signup" [] signup)
  (GET "/activation" [] activation)
  (POST "/forget_password" [] forget-password)
  (GET "/reset_password" [] reset-password)
  (GET "/settings" [] settings)
  (GET "/settings/password" [] settings-password)
  (POST "/settings/password" [] settings-change-password)
  (GET "/settings/profile" [] settings-profile)
  (GET "/admin/users" [] admin-users))
