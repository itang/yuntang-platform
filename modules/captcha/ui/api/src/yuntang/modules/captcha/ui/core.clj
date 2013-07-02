(ns yuntang.modules.captcha.ui.core
  (:require [cljwtang.datatype :refer [new-ui-module]]
            [yuntang.modules.captcha.ui.handlers :refer [captcha-routes]]))

(def module (new-ui-module {:routes [captcha-routes]}))
