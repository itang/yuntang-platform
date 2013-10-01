(ns yuntang.user.bootstrap
  (:require [cljwtang.lib :refer [new-bootstrap-task]]
            [yuntang.user.core :refer :all]))

(def ^:const p-admin-permit-name "platform.admin")

(def ^:const p-dev-permit-name "platform.dev")

(defn- init-data []
  (let [_ (create-user!
            {:username "admin"
             :password "admin123"
             :email "cljwtang@qq.com"
             :realname "系统管理员"
             :enabled true})
        _ (create-user!
             {:username "user"
              :password "user123"
              :email "cljwtang.user@qq.com"
              :realname "普通用户"
              :enabled true})
        _ (create-permit! {:name p-admin-permit-name})
        _ (create-permit! {:name p-dev-permit-name})
        admin (find-user-by-username "admin")
        user (find-user-by-username "user")
        admin-permit (find-permit-by-property :name p-admin-permit-name)
        dev-permit (find-permit-by-property :name p-dev-permit-name)]
    (bind-user-permits! admin dev-permit admin-permit)
    (bind-user-permits! user dev-permit)))

(def bootstrap-tasks
  (map
    new-bootstrap-task
    [{:name "用户数据初始化"
         :run? #(not (exists-user? "admin"))
         :run-fn init-data
         :sort 0}]))
