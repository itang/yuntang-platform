(ns yuntang.user.config
  (:require [cljwtang.lib :refer :all]))

(defn captcha-enabled? []
  (*app-config-fn* :platform.captcha-enabled? false))

(defn activation-url-prefix []
  (str "http://" (hostaddr) "/activation"))

(defn user-regist-activation-mode []
  (*app-config-fn* :user-regist-activation-mode :by-email))

(defn reset-password-url-prefix []
  (str "http://" (hostaddr) "/reset_password"))
