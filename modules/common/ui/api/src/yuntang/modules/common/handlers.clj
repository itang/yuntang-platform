(ns yuntang.modules.common.handlers
  (:require [clojure.string :as str]
            [clojure.tools.logging :as log]
            [cljtang.core :refer :all]
            [cljwtang.lib :refer :all]
            [yuntang.modules.common.appconfig.core :as appconfigs]))

(defonce ignored-namespaces (atom #{}))

(defn reload-all []
  (doseq [n (remove (comp @ignored-namespaces ns-name) (all-ns))]
    (try
      #_(when (not-every? #(.startsWith (str n) %) ["clojure" "stencil"])
        (require (ns-name n) :reload :verbose))
      (require (ns-name n) :reload :verbose)
      (catch Exception e
        (log/warn (str e))))))

(defhandler admin-appconfigs 
  {:perm "admin"}
  []
  (let [items (map #(assoc %
                      :created-at (format-date (:created_at %))
                      :updated-at (some-> (:updated_at %) format-date))
                   (appconfigs/find-all))]
    (view "modules/common/appconfig" {:appconfigs (maplist-with-no items)})))

(defhandler reload [req]
  (let [remote-addr (:remote-addr req)]
    (if (= remote-addr "127.0.0.1")
      (do
        (log/warn "System exit by dev request...")
        ;; 先暴力退出  TODO improve
        (System/exit 0))
      (log/warn "request from" remote-addr))))

(defroutes common-routes
  (GET "/admin/appconfigs" [] admin-appconfigs)
  (GET "/_dev/reload" [] reload))
