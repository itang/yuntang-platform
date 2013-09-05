(ns wapp.module
  (:require [cljwtang.lib :refer :all]
            [wapp.handlers.examples :refer :all]))

(def module
  (new-ui-module
    {:name "example app"
     :routes [@examples-routes]
     :fps [examples-index-fp fileupload-index-fp]
     :menus (maps->menus
              [{:id "examples"
                :name "Examples"
                :funcpoint examples-index-fp
                :parent "dashboard"}
               {:name "File Upload"
                :funcpoint fileupload-index-fp
                :parent "dashboard"}])
     :snippets-ns ['wapp.views.snippets]}))
