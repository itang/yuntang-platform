(ns wapp.server
  (:require [cljwtang.lib :refer :all]
            [wapp.config :as wappcofig]
            [yuntang.user.core :as user-core]
            [yuntang.user.module :as user-module]
            [yuntang.layout.module :as layout-module]
            [yuntang.admin.module :as admin-module]
            [yuntang.modules.captcha.module :as captcha-module]
            [yuntang.modules.common.module :as common-module]
            [wapp.module :as wapp]))

(create-app
  (fn []
    (info "set db-config...")
    (set-db-config! wappcofig/db-config)
    (info "regist modules...")
    (regist-modules!
       layout-module/module
       captcha-module/module
       user-module/module
       wapp/module
       admin-module/module
       common-module/module)))

(require '[cljwtang.server :as server])

(def app server/app)

(def init server/init)

(defn -main [& args]
  (server/start-server))
