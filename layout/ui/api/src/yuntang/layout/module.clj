(ns yuntang.layout.module
  (:require [cljwtang.lib :refer :all]
            [yuntang.layout.handlers :refer [layout-routes]]))

(def fp-dashboard
  (new-funcpoint {:name "dashboard"
                  :url "/"}))

(def module
  (new-ui-module
    {:name "layout"
     :init (fn [_]
             (log-info "set not found content ...")
             (set-not-found-content! (render-file "common/404" nil))
             (log-info "set-unauthorized-handler ...")
             (set-unauthorized-handler! (fn [req] (view "common/401" {:req req}))))
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
