(ns yuntang.user.module
  (:require [cljwtang.lib :refer :all]
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
             (info "set user-logined?-fn...")
             (set-user-logined?-fn! user-logined?)
             (info "set current-user-fn...")
             (set-current-user-fn! current-user))
     :routes [account-routes]
     :menus (maps->menus
              [{:id "amdin.users"
                :name "用户管理"
                :funcpoint fp-admin-users
                :parent "admin"}])
     :snippets-ns ['yuntang.user.snippets]
     :bootstrap-tasks bootstrap-tasks}))
