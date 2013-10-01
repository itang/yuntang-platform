(ns yuntang.user.handlers
  (:require [clojure.string :refer [trim]]
            [cemerick.friend :as friend]
            [cljtang.lib :refer :all]
            [cljwtang.lib :refer :all]
            [yuntang.user.core :refer :all]
            [yuntang.user.util :refer [check-username-pattern]]
            [yuntang.user.config :refer :all]
            [yuntang.modules.captcha.config :refer [captcha-enabled?]]
            [yuntang.modules.captcha.validates :refer [validate-captcha]]))

(defn- password? [password]
  (if-let [password (some-> password trim)]
    (when (< (count password) 6 )
      "密码至少6位")
    "密码为空"))

(defn- username? [username]
  (if-let [username (some-> username trim)]
    (let [check-result (check-username-pattern username)]
      (cond
       (not-nil? check-result) check-result
       (exists-user? username) (str username "已经被注册")))
    "用户名为空"))

(defn- email? [email]
  (if-let [email (some-> email trim)]
    (cond
     (exists-user-by-email? email) (str email "已经被注册")
     :else nil)
    "email为空"))

(defn- check-email? [email]
  (when (not (exists-user-by-email? email))
    (str email "不存在")))

(defn- redirect-signin-page [& [active-form]]
  (redirect
   (when-> "/signin" active-form #(str % "?active-form=" active-form))))

(defn- validate-username-input-rules [username]
  (let [username (some-> username trim)]
    (and (validate-rule (has-value? username) [:username "Please input your username."])
         (validate-rule (and (min-length? username 4) (max-length? username 20))
                        [:username "用户名长度4-20"]))))

(defn- validate-password-input-rules [password]
  (let [password (some-> password trim)]
    (and (validate-rule (has-value? password) [:password "Please input your password."])
         (validate-rule (and (min-length? password 6) (max-length? password 20))
                        [:username "密码长度6-20"]))))

(defn- validate-email-input-rules [email]
  (let [email (some-> email trim)]
    (and (validate-rule (has-value? email) [:email "Please input your email."])
         (validate-rule (is-email? email) [:email "邮件帐号格式不正确!"]))))

(defn- validate-signin-form-rules [username password]
  (and
   (validate-username-input-rules username)
   (validate-username-input-rules password)))

(defn- validate-user-status-rules [user]
  (and (validate-rule (not-nil? user) [:username "用户不存在或者密码不正确"])
       (validate-rule (:enabled user) [:username "帐户未激活或帐户未启用"])
       (validate-rule (:account_non_expired user) [:username "帐户已经过期"])
       (validate-rule (:credentials_non_expired user) [:username "帐户认证信息已经过期"])
       (validate-rule (:account_non_locked user) [:username "帐户已经被锁定"])))

(defn- validate-forget-password-form-rules [email]
  (and (validate-email-input-rules email)
       (let [err (check-email? email)]
         (validate-rule (not err) [:email err]))))

(defn- validate-settings-change-password-form-rules
  [current_password password user_password_confirmation]
  (and (validate-password-input-rules current_password)
       (validate-password-input-rules password)
       (validate-password-input-rules user_password_confirmation)))

(defn- validate-signup-form-rules [username password email]
  (and (validate-username-input-rules username)
       (let [err (username? username)]
         (validate-rule (not err) [:username err]))
       (validate-password-input-rules password)
       (validate-email-input-rules email)
       (let [err (email? email)]
         (validate-rule (not err) [:email err]))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(with-routes account-routes ""
  (defhandler signin-page
    "登录页"
    {:get "/signin"
     :anti-forgery true}
    [active-form req]
    (let [postback-params (postback-params)]
      (view "signin"
            {:captcha-enabled? (captcha-enabled?)
             :ori-active-form active-form
             :active-form (case active-form
                            "second" "eq(1)"
                            "three" "eq(2)"
                            nil)
             :params postback-params})))

  (defhandler signin
    "登录"
    {:post "/signin"
     :anti-forgery true
     :validate
     '(and
       (validate-signin-form-rules username password)
       (validate-captcha)
       (validate-user-status-rules (check-user username password)))
     :on-validate-error
     '(do (log-warn username "登录失败!")
          (redirect-signin-page))}
    [username password req]
    (let [user (find-user-in-uid-username-email-for-session username)]
      (log-info username "登录成功!")
      #_(set-current-user! user)
      (friend/merge-authentication
        (redirect "/")
        user)))

  (defhandler logout
    "注销"
    {:get "/logout"}
    []
    #_(session-remove! :user)
    (friend/logout* (redirect-signin-page)))

  (defhandler signup
    "注册"
    {:post "/signup"
     :anti-forgery true
     :validate '(validate-signup-form-rules username password email)
     :on-validate-error (redirect-signin-page "second")}
    [username password email realname type req]
    (let [email (trim email)
          activation-mode (user-regist-activation-mode)
          activation-mode-by-email (= :by-email activation-mode)
          activation-mode-auto (= :auto activation-mode)
          activation-mode-by-manual (= :by-manual activation-mode)
          activation-code (uuid :simplify)]
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
          (log-info "发送用户注册激活邮件")
          (send-mail-by-template
           {:to email :subject (str "欢迎注册wapp(wapp.com)，请激活你的帐号")}
           "mail-activation-template"
           {:activation-url activation-url
            :email email})))
      (view "signup-submit-finish"
            {:email email
             :mail-vendor (mail-vendor-by-email-account email)
             :by-email activation-mode-by-email
             :auto activation-mode-auto
             :by-manual activation-mode-by-manual})))

  (defhandler activation
    "用户注册邮件激活"
    {:get "/activation"}
    [code]
    (if-not (= 32 (count code))
      (not-found "")
      (let [user (activation-user! code)]
        (view "activation-result" {:user user}))))

  (defhandler forget-password
    "提交忘记密码请求"
    {:post "/forget-password"
     :anti-forgery true
     :validate '(validate-forget-password-form-rules email)
     :on-validate-error (redirect-signin-page "three")}
    [email]
    (let [password-reset-code
          (uuid :simplify)
          reset-url
          (str (reset-password-url-prefix)
               "?email=" email
               "&code=" password-reset-code)]
      (set-password-reset-code! email password-reset-code)
      (send-mail-by-template {:to email
                              :subject (str "[wapp]-" "申请重置密码邮件")}
                             "forget-password-template"
                             {:reset-url reset-url})
      (view "forget-password-result"
            {:mail-vendor (mail-vendor-by-email-account email)})))

  (defhandler reset-password
    "重置密码"
    {:get "/reset-password"}
    [email code]
    (if-not (= 32 (count code))
      (not-found "")
      (let [p (reset-password! email code)
            ctx {:new-password p}]
        (log-debug email)
        (send-mail-by-template
         {:to email :subject (str "[wapp]-" "重置密码邮件通知")}
         "reset-password-notice-template"
         ctx)
        (view "reset-password-result" ctx))))

  (defhandler settings
    "个人设置页"
    {:get "/settings" :auth true}
    []
    (redirect "/settings/password"))

  (defhandler settings-profile
    "个人设置-帐户"
    {:get "/settings/profile" :auth true}
    []
    (view "settings/profile" {:channel "profile"}))

  (defhandler settings-password
    "个人设置-密码"
    {:get "/settings/password"
     :anti-forgery true
     :auth true}
    []
    (view "settings/password" {:channel "password"}))

  (defhandler settings-change-password
    "修改密码"
    {:post "/settings/password"
     :anti-forgery true
     :auth true
     :validate '(and (validate-settings-change-password-form-rules
                      current_password password user_password_confirmation)
                     (validate-rule (is-current-user-password current_password)
                                    [:current_password "当前用户密码不对"])
                     (validate-rule (= password user_password_confirmation)
                                    [:password "两次输入的密码不一致"]))
     :on-validate-error (redirect "/settings/password")}
    [current_password password user_password_confirmation]
    (change-current-user-password! password)
    (flash-msg (success-message "更改用户密码成功!"))
    (redirect "/settings/password"))

  ;; 用户管理
  (defhandler admin-users
    {:get "/admin/users"
     :perm "platform.admin"
     :fp "用户管理"}
    []
    (let [today (moment-format "yyyy-MM-dd")
          users (map #(-> %
                          (assoc :type-name (user-types-map (:type %))))
                     (all-users))]
      (view "admin/users" {:users users}))))
