(ns yuntang.user.ui.config
  (:require [cljwtang.config :as config]
            [yuntang.modules.common.appconfig.core :refer [app-config]]))

(defn captcha-enabled? []
  (app-config :platform.captcha-enabled? false))

(defn activation-url-prefix []
  (str "http://" (config/hostaddr) "/activation"))

(defn user-regist-activation-mode []
  (app-config :user-regist-activation-mode :by-email))

(defn reset-password-url-prefix []
  (str "http://" (config/hostaddr) "/reset_password"))
