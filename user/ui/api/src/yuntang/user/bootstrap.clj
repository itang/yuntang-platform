(ns yuntang.user.bootstrap
  (:require [cljwtang.lib :refer [new-bootstrap-task]]
            [yuntang.user.core :refer :all]))

(defn- init-data []
  (let [_ (create-user!
            {:username "admin"
             :password "admin123"
             :email "live.tang@gmail.com"
             :realname "系统管理员"
             :enabled true})
        _ (create-user!
             {:username "itang"
              :password "computer"
              :email "livetang@qq.com"
              :realname "堂堂"
              :enabled true})
        _ (create-permit! {:name "admin"})
        _ (create-permit! {:name "user"})
        admin (find-user-by-username "admin")
        user (find-user-by-username "itang")
        admin-permit (find-permit-by-property :name "admin")
        user-permit (find-permit-by-property :name "user")]
    (bind-user-permit admin user-permit)
    (bind-user-permit admin admin-permit)
    (bind-user-permit user user-permit)))

(def bootstrap-tasks
  (map
    new-bootstrap-task
    [{:name "用户数据初始化"
         :run? #(not (exists-user? "admin"))
         :run-fn init-data
         :sort 0}]))
