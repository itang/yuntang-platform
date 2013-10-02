(ns {{name}}.bootstrap
  (:require [cljwtang.lib :refer [new-bootstrap-task]]
            [{{name}}.models :refer :all :as models]))

#_(def bootstrap-tasks
  (map
    new-bootstrap-task
    [{:name "用户数据初始化"
         :run? #(not (exists-user? "admin"))
         :run-fn init-data
         :sort 0}]))
