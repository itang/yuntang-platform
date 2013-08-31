(ns wapp.server
  (:refer-clojure :exclude [name sort])
  (:require
    [cljwtang.inject 
     :as inject
     :refer [inject-fn-app-config
     inject-fn-user-logined?
     inject-fn-current-user
     inject-db-config
     inject-not-found-content]]
    [cljwtang.core :refer [render-file]]
    [yuntang.modules.common.appconfig.core :refer [app-config]]
    [wapp.config :as wappcofig]))

(inject-db-config wappcofig/db-config)

(inject-fn-app-config app-config)

(require 
    '[yuntang.user.core :as user-core]
    '[yuntang.user.module :as user-module]
    '[yuntang.layout.module :as layout-module]
    '[yuntang.admin.module :as admin-module]
    '[yuntang.modules.captcha.module :as captcha-module]
    '[yuntang.modules.common.module :as common-module]
    '[wapp.module :as wapp])

(inject-not-found-content (render-file "common/404" nil))

(inject/regist-modules!
  layout-module/module
  captcha-module/module
  user-module/module
  wapp/module
  admin-module/module
  common-module/module)

(require '[cljwtang.server :as server])

(def app server/app)

(def init server/init)

(defn -main [& args]
  (server/start-server))
