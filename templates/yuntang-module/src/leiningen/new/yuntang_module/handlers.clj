(ns {{name}}.handlers
  (:require [cljtang.lib :refer :all]
            [cljwtang.lib :refer :all]
            [{{name}}.models :refer :all :as models]))

#_(with-routes {{name}}-routes "/{{name}}"
  (defhandler index
    {:path ""
     :fp "模块主页"}
    [req]
    (view "/{{name}}/index")))
