(ns yuntang.layout.core
  (:require [cljwtang.datatype :refer [new-ui-module new-funcpoint maps->menus]]
            [yuntang.layout.handlers.welcome :refer [welcome-routes]]))

(def fp-dashboard
  (new-funcpoint {:name "dashboard"
                  :url "/"}))

(def module
  (new-ui-module {:routes [welcome-routes]
                  :fps [fp-dashboard]
                  :menus (maps->menus
                           [{:id "dashboard"
                             :name "Dashboard"
                             :attrs {:classname "icon-home"}
                             :sort -1}
                            {:name "Dashboard"
                             :funcpoint fp-dashboard
                             :attrs {:classname "icol-dashboard"}
                             :parent "dashboard"
                             :sort -1}])
                  :snippets-ns ['yuntang.layout.views.snippets]}))
