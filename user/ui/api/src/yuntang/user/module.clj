(ns yuntang.user.module
  (:require
  [cljwtang.inject 
   :as inject
   :refer [inject-fn-app-config
   inject-fn-user-logined?
   inject-fn-current-user
   inject-db-config
   inject-not-found-content]])
  (:require [cljwtang.datatype :refer [new-ui-module new-funcpoint maps->menus]]
            [yuntang.user.core :refer :all]
            [yuntang.user.handlers :refer [account-routes]]
            [yuntang.user.bootstrap :refer [bootstrap-tasks]]))

(def fp-admin-users
  (new-funcpoint {:name "admin users"
                  :url "/admin/users"}))

(def module
  (new-ui-module 
    {:name "user"
     :init (fn [m]
             (inject-fn-user-logined? user-logined?)
             (inject-fn-current-user current-user))
     :routes [account-routes]
     :menus (maps->menus
              [{:id "amdin.users"
                :name "用户管理"
                :funcpoint fp-admin-users
                :parent "admin"}])
     :snippets-ns ['yuntang.user.snippets]
     :bootstrap-tasks bootstrap-tasks}))
