(ns yuntang.modules.common.module
  (:require [cljwtang.datatype :refer [new-ui-module new-funcpoint maps->menus]]
            [yuntang.modules.common.handlers :refer [common-routes]]))

(def fp-admin-appconfigs
  (new-funcpoint {:name "admin.appconfigs"
                  :url "/admin/appconfigs"
                  :perm "admin.appconfigs"}))

(def module
  (new-ui-module 
    {:name "common"
     :routes [common-routes]
     :fps [fp-admin-appconfigs]
     :menus (maps->menus
              [{:name "应用配置"
                :funcpoint fp-admin-appconfigs
                :attrs {:classname "icol-user"}
                :parent "admin"}])}))
