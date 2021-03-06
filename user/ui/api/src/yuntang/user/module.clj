(ns yuntang.user.module
  (:require [cljwtang.lib :refer :all]
            [yuntang.user.core :as user]
            [yuntang.user.handlers :refer :all]
            [yuntang.user.bootstrap :refer [bootstrap-tasks]]))

(def module
  (new-ui-module 
    {:name "user"
     :init (fn [m]
             (log-info "set user-logined?-fn...")
             (set-user-logined?-fn! user/user-logined?)
             (log-info "set current-user-fn...")
             (set-current-user-fn! user/current-user)
             (log-info "set load-credentials-fn...")
             (set-load-credentials-fn! user/load-credentials))
     :routes [account-routes]
     :fps [admin-users-fp]
     :menus (maps->menus
              [{:id "amdin.users"
                :name "用户管理"
                :funcpoint admin-users-fp
                :parent "admin"}])
     :snippets-ns ['yuntang.user.snippets]
     :bootstrap-tasks bootstrap-tasks}))
