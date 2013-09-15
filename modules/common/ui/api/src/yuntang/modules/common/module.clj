(ns yuntang.modules.common.module
  (:require [cljwtang.lib :refer :all]
            [yuntang.modules.common.appconfig.core :refer [app-config]]
            [yuntang.modules.common.handlers :refer [common-routes]]))

(def fp-admin-appconfigs
  (new-funcpoint {:name "admin.appconfigs"
                  :url "/admin/appconfigs"
                  :perm "admin.appconfigs"}))

(def module
  (new-ui-module 
    {:name "common"
     :init (fn [m]
             (info "set app-config fn...")
             (set-app-config-fn! app-config))
     :sort -1
     :routes [common-routes]
     :fps [fp-admin-appconfigs]
     :menus (maps->menus
              [{:name "应用配置"
                :funcpoint fp-admin-appconfigs
                :attrs {:classname "icol-user"}
                :parent "admin"}])}))
