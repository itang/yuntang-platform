(ns lobos.config
  (:require [lobos.connectivity :refer [open-global]]
            [wapp.config :refer [db-config]]))

(open-global db-config)
