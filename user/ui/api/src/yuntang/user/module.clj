(ns yuntang.user.module
  (:require [cljwtang.datatype :refer [new-ui-module new-funcpoint maps->menus]]
            [yuntang.user.handlers :refer [account-routes]]
            [yuntang.user.bootstrap :refer [bootstrap-tasks]]))

(def fp-admin-users
  (new-funcpoint {:name "admin users"
                  :url "/admin/users"}))

(def module
  (new-ui-module {:routes [account-routes]
                  :menus (maps->menus
                           [{:id "amdin.users"
                             :name "用户管理"
                             :funcpoint fp-admin-users
                             :parent "admin"}])
                  :snippets-ns ['yuntang.user.snippets]
                  :bootstrap-tasks bootstrap-tasks}))
