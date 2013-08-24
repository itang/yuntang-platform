(ns yuntang.admin.module
  (:require [cljwtang.datatype :refer [new-ui-module new-funcpoint maps->menus]]
            [yuntang.admin.handlers :refer [admin-routes]]))

(def fp-admin-envinfo 
  (new-funcpoint {:name "admin.envinfo"
                  :url "/admin/envinfo"
                  :perm "admin.envinfo"}))

(def module
  (new-ui-module {:routes [admin-routes]
                  :fps [fp-admin-envinfo]
                  :menus (maps->menus
                           [{:id "admin"
                             :name "系统管理"
                             :attrs {:classname "icon-gift"}}
                            {:name "运行环境"
                             :funcpoint fp-admin-envinfo
                             :attrs {:classname "icol-user"}
                             :parent "admin"}])}))
