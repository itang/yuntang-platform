(ns lobos.config
  (:require [lobos.connectivity :refer [open-global]]
            [wapp.config :refer [db-config]]
            [cljwtang.lib :refer [log-warn]]))

;; hacked for lein check
(try
  (open-global db-config)
  (catch Exception e
    (log-warn "open-global" e)))
