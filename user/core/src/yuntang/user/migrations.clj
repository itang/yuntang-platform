(ns yuntang.user.migrations
  (:refer-clojure :exclude [alter drop
                            bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]] core schema config))
  (:use cljwtang.tools.lobos-helpers))

(defmigration add-users-table
  (up [] (create
          (tbl :users
               (varchar :uid 20 :unique)
               (varchar :username 100 :unique)
               (varchar :realname 100)
               (varchar :nickname 50)
               (varchar :crypted_password 100)
               (varchar :email 100)
               (varchar :mobile_phone 100)
               (varchar :type 20 (default "individual"))
               (varchar :locale 10)
               (varchar :link 200)
               (varchar :picture 200)
               
               ;;冗余信息.person
               (varchar :gender 10)
               (timestamp :birthday)
               
               (boolean :enabled (default false))
               (boolean :account_non_expired (default true))
               (boolean :credentials_non_expired (default true))
               (boolean :account_non_locked (default true))
               (varchar :activation_code 32)
               (timestamp :activation_code_created_at)
               (varchar :password_reset_code 32)
               (timestamp :password_reset_code_created_at)
               (varchar :new_requested_email 100)
               (varchar :email_change_code 32)
               (check :username (> (length :username) 4)))))
  (down [] (drop (table :users))))
