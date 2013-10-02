(ns {{name}}.server
  (:require [cljwtang.lib :as cljwtang :refer :all]
            [{{name}}.config :as wappcofig])
  (:gen-class))

(cljwtang/create-app
  (fn []
    (log-info "set db-config...")
    (set-db-config! wappcofig/db-config)))

(require '[cljwtang.server :as server])

(def app server/app)

(def init server/init)

(defn -main
  "Application entry point"
  [& args]
  (server/start-server))
