(ns yuntang.modules.captcha.module
  (:require [cljwtang.datatype :refer [new-ui-module]]
            [yuntang.modules.captcha.handlers :refer [captcha-routes]]))

(def module 
  (new-ui-module 
    {:name "captcha"
     :routes [captcha-routes]}))
