(ns yuntang.user.core
  (:require [cljtang.core :refer :all]
            [cljtang.util :refer [uuid->hash->id]]
            [noir.session :as session]
            [korma.core :refer :all]
            [korma.db :refer [transaction]]
            [cljwtang.utils.scrypt :as scrypt]
            [crypto.random :as random]))

;; 用户类型
(def user-types
  [{:value "individual"
    :name "个人"}
   {:value "company"
    :name "企业"}
   {:value "government"
    :name "机构"}
   {:value "organization"
    :name "组织"}])

(def user-types-map
  (into {} (for [{:keys [name value]} user-types] [value name])))

;; 注册激活（审核）模式
(def user-regist-activation-modes-map
  {:auto "自动激活"
   :by-email "邮件激活"
   :by-manual "人工激活"})

(defentity users
  #_(entity-fields :id                             ; id
                   :uid                            ; uid
                   :username                       ; 用户名
                   :realname                       ; 全名|真实名称
                   :nickname                       ; 昵称
                   :email                          ; 邮件
                   :mobile_phone                   ; 移动电话号码
                   :crypted_password               ; 加密密码
                   :type                           ; 用户类型
                   :locale                         ; 区域
                   :link                           ; 主页
                   :picture                        ; 图片

                   ;;冗余信息.person
                   :gender                         ; 性别
                   :birthday                       ; 生日

                   :enabled                        ; 帐户有效
                   :account_non_expired            ; 帐户未过期
                   :credentials_non_expired        ; 凭据未过期
                   :account_non_locked             ; 帐户未锁定
                   :activation_code                ; 激活码
                   :activation_code_created_at     ; 激活码创建时间
                   :password_reset_code            ; 密码重置码
                   :password_reset_code_created_at ; 密码重置码创建时间
                   :new_requested_email            ; 新邮件地址
                   :email_change_code              ; 邮件改变码
                   :created_at                     ; 创建时间
                   :updated_at                     ; 更新时间
                   )
  )

(def user-session-key :user)

;;;; PUBLIC API

(defn all-users []
  (select users))

(defn find-user-by-property [p v]
   (first (select users
          (where {p v}))))

(defn find-user-by-username [username]
  (find-user-by-property :username username))

(defn find-user-by-email [email]
  (find-user-by-property :email email))

(defn find-user-by-uid [uid]
 (find-user-by-property :uid uid))

(defn find-user-in-uid-username-email [s]
   (first (select users
          (where (or {:uid s}
                     {:username s}
                     {:email s})))))

(defn find-user-by-activation-code [code]
  (find-user-by-property :activation_code code))

(defn find-user-by-password-reset-code [code]
  (find-user-by-property :password_reset_code code))

(defn create-user!
  "创建用户"
  [m]
  (transaction
    (let [m (when-not-> m :crypted_password
              (assoc m :crypted_password (scrypt/encrypt (:password m))))
          m (dissoc m :password)
          m (assoc m :uid (uuid->hash->id))]
      (insert users (values m)))))

(defn activation-user! [activation-code]
  (when-let [user (find-user-by-activation-code activation-code)]
    (when-not (:enabled user)
      (update users
        (set-fields {:enabled true
                     :activation_code nil
                     :activation_code_created_at nil})
        (where {:id (:id user)}))
      user)))

(defn set-password-reset-code!
  "设置用户重置密码的code"
  [email code]
  (update users
    (set-fields {:enabled true
                 :password_reset_code code
                 :activation_code_created_at (moment)})
    (where {:email email})))

(defn reset-password! [email code]
  (when-let [user (find-user-by-password-reset-code code)]
    (let [new-pwd (random/hex 4)]
      (update users
        (set-fields {:crypted_password (scrypt/encrypt new-pwd)
                     :password_reset_code nil
                     :password_reset_code_created_at nil})
        (where {:email email}))
      new-pwd)))

(defn check-user
  "检查用户是否存在."
  [login-name password]
  (when-let [user (find-user-in-uid-username-email login-name)]
    (when (scrypt/verify password (:crypted_password user))
      user)))

(defn exists-user? [username]
  (not-nil? (find-user-by-username username)))

(defn exists-user-by-email? [email]
  (not-nil? (find-user-by-email email)))

(defn set-current-user! [user]
  (session/put! user-session-key user))

(defn user-type-name [user-type]
  (get user-types-map
       (if (map? user-type) (:type user-type) user-type)))

(defn current-user
  "当前登录用户"
  []
  (session/get user-session-key))

(defn user-logined?
  "用户是否登入?"
  []
  (not-nil? (current-user)))

(defn find-user-in-uid-username-email-for-session [login-name]
  (let [user (find-user-in-uid-username-email login-name)
        keys [:id :username :realname :nickname :email :type :crypted_password]
        user (select-keys user keys)]
    (assoc user :type-name (user-type-name user))))

(defn is-current-user-password [password]
  (scrypt/verify password (:crypted_password (current-user))))

(defn change-current-user-password! [new-pwd]
  (update users
          (set-fields {:crypted_password (scrypt/encrypt new-pwd)})
          (where {:id (:id (current-user))})))
