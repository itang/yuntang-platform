(ns wapp.server
  (:refer-clojure :exclude [name sort])
  (:require
    [cljwtang.datatype :refer :all]
    [cljwtang.inject :refer [inject-snippets-ns
                             inject-fn-app-config
                             inject-routes
                             inject-bootstrap-tasks
                             inject-fn-user-logined?
                             inject-fn-current-user
                             inject-db-config
                             inject-not-found-content]]
    [cljwtang.template.selmer :as selmer]
    [cljwtang.core :refer [render-file]]
    [yuntang.layout.inject :refer [inject-menus]]
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

(defn modules []
  [layout-module/module
   captcha-module/module
   user-module/module
   wapp/module
   admin-module/module
   common-module/module])

(defn- flatten-by [f]
  (->> (modules) (map f) flatten))

(inject-snippets-ns (flatten-by snippets-ns))

(inject-menus (flatten-by menus))

(inject-routes (flatten-by routes))

(inject-bootstrap-tasks (flatten-by bootstrap-tasks))

(inject-fn-user-logined? user-core/user-logined?)

(inject-fn-current-user user-core/current-user)

(inject-not-found-content (render-file "common/404" nil))

(require '[cljwtang.server :as server])

(def app server/app)

(def init server/init)

(defn -main [& args]
  (server/start-server))
