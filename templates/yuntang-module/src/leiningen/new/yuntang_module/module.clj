(ns {{name}}.module
  (:require [cljwtang.lib :refer :all]
            [{{name}}.models :as models]
            [{{name}}.handlers :refer :all]
            #_[{{name}}.bootstrap :refer [bootstrap-tasks]]))

#_(def module
  (new-ui-module 
    {:name "{{name}}"
     :init (fn [m]
             )
     :routes [{{name}}-routes]
     :fps [index-fp]
     :menus (maps->menus
              [{:id "{{name}}.index"
                :name "模块主页"
                :funcpoint index-fp
                :parent ""}])
     :snippets-ns ['{{name}}.snippets]
     :bootstrap-tasks bootstrap-tasks}))
