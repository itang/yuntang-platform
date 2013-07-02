(ns yuntang.admin.ui.core
  (:require [cljwtang.datatype :refer [new-ui-module new-funcpoint maps->menus]]
            [yuntang.admin.ui.handlers.envinfo :refer [envinfo-routes]]))

(def fp-admin-envinfo 
  (new-funcpoint {:name "admin.envinfo"
                  :url "/admin/envinfo"
                  :perm "admin.envinfo"}))

(def module
  (new-ui-module {:routes [envinfo-routes]
                  :fps [fp-admin-envinfo]
                  :menus (maps->menus
                           [{:id "admin"
                             :name "系统管理"
                             :attrs {:classname "icon-gift"}}
                            {:name "运行环境"
                             :funcpoint fp-admin-envinfo
                             :attrs {:classname "icol-user"}
                             :parent "admin"}])}))
