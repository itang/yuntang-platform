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
    '[yuntang.user.ui.core :as user-ui]
    '[yuntang.layout.core :as layout-ui]
    '[yuntang.admin.ui.core :as admin-ui]
    '[yuntang.modules.captcha.ui.core :as captcha-ui]
    '[yuntang.modules.common.ui.core :as common-ui]
    '[wapp.core :as wapp])

(defn modules [] 
  [layout-ui/module
              captcha-ui/module
              user-ui/module
              wapp/module
              admin-ui/module
              common-ui/module])

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
