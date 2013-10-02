(ns lobos.config
  (:require [cljwtang.lib :refer [log-warn]] 
            [lobos.connectivity :refer [open-global]]
            [{{name}}.config :refer [db-config]]))

;; hacked for lein check
(try
  (open-global db-config)
  (catch Exception e
    (log-warn "open-global" e)))
