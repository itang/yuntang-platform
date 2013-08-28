(ns yuntang.modules.common.handlers
  (:require [clojure.string :as str]
            [clojure.tools.logging :as log]
            [compojure.core :refer :all]
            [cljtang.core :refer :all]
            [cljwtang.core :refer :all]
            [cljwtang.view :refer :all]
            [yuntang.modules.common.appconfig.core :as appconfigs]))

(defhandler index []
  (let [items (map #(assoc %
                      :created-at (format-date (:created_at %))
                      :updated-at (some-> (:updated_at %) format-date))
                   (appconfigs/find-all))]
    (view "modules/common/appconfig" {:appconfigs (maplist-with-no items)})))

(defonce ignored-namespaces (atom #{}))

(defn reload-all []
  (doseq [n (remove (comp @ignored-namespaces ns-name) (all-ns))]
    (try
      #_(when (not-every? #(.startsWith (str n) %) ["clojure" "stencil"])
        (require (ns-name n) :reload :verbose))
      (require (ns-name n) :reload :verbose)
      (catch Exception e
        (log/warn (str e))))))

(defhandler reload [req]
  (let [remote-addr (:remote-addr req)]
    (if (= remote-addr "127.0.0.1")
      (do
        (log/warn "System exit by dev request...")
        (System/exit 0) ;; 先暴力退出  TODO improve
        )
      #_(do
        (log/info "invalidate-cache")
        (invalidate-cache)
        (log/info "reload-all")
        (reload-all)
        #_(refresh)
        #_(require 'wapp.server
                 :reload-all
                 :verbose)
        (json-success-message "finished"))
      (log/warn "request from" remote-addr))))

(defroutes common-routes
  (GET "/admin/appconfigs" [] index)
  (GET "/_dev/reload" [] reload))