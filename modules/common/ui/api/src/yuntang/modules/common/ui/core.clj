(ns yuntang.modules.common.ui.core
  (:require [cljwtang.datatype :refer [new-ui-module new-funcpoint maps->menus]]
            [yuntang.modules.common.ui.handlers
                [appconfig :refer [appconfig-routes]]
                [dev :refer [dev-routes]]]))

(def fp-admin-appconfigs
  (new-funcpoint {:name "admin.appconfigs"
                  :url "/admin/appconfigs"
                  :perm "admin.appconfigs"}))

(def module
  (new-ui-module {:routes [appconfig-routes dev-routes]
                  :fps [fp-admin-appconfigs]
                  :menus (maps->menus
                           [{:name "应用配置"
                             :funcpoint fp-admin-appconfigs
                             :attrs {:classname "icol-user"}
                             :parent "admin"}])}))
