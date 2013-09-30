(ns yuntang.modules.captcha.config
  (:require [cljwtang.lib :refer :all]))

(defn captcha-enabled? []
  (*app-config-fn* :platform.captcha-enabled? false))
