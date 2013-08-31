(ns yuntang.layout.module
  (:require [cljwtang.datatype :refer [new-ui-module new-funcpoint maps->menus]]
            [yuntang.layout.handlers :refer [layout-routes]]))

(def fp-dashboard
  (new-funcpoint {:name "dashboard"
                  :url "/"}))

(def module
  (new-ui-module 
    {:name "layout"
     :routes [layout-routes]
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
     :snippets-ns ['yuntang.layout.snippets]}))
