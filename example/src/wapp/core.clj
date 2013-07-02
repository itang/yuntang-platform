(ns wapp.core
  (:require [cljwtang.datatype :refer [new-ui-module new-funcpoint maps->menus]]
            [wapp.handlers.examples :refer [examples-routes]]))

(def fp-examples
  (new-funcpoint {:name "examples"
                  :url "/examples"}))

(def fp-examples-fileupload
  (new-funcpoint {:name "examples.fileupload"
                  :url "/examples/fileupload"}))

(def module
  (new-ui-module {:routes [examples-routes]
                  :fps [fp-examples fp-examples-fileupload]
                  :menus (maps->menus
                           [{:id "examples"
                             :name "Examples"
                             :funcpoint fp-examples
                             :parent "dashboard"}
                            {:name "File Upload"
                             :funcpoint fp-examples-fileupload
                             :parent "dashboard"}])
                  :snippets-ns ['wapp.views.snippets]}))
