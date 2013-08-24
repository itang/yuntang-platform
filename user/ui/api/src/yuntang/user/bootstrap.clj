(ns yuntang.user.bootstrap
  (:require [cljwtang.datatype :refer [new-bootstrap-task]]
            [yuntang.user.core :refer :all]))

(def bootstrap-tasks
  (map
    new-bootstrap-task
    [{:name "用户数据初始化"
         :run? #(not (exists-user? "admin"))
         :run-fn #(create-user!
                    {:username "admin"
                     :password "admin123"
                     :email "live.tang@gmail.com"
                     :realname "系统管理员"
                     :enabled true})
         :sort 0}]))
