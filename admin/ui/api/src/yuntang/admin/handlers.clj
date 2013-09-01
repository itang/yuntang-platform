(ns yuntang.admin.handlers
  (:require [clojure.string :as str]
            [clojure.core.memoize :as memoize]
            [cljtang.core :refer :all]
            [cljtang.runtime :as runtime]
            [cljwtang.lib :refer :all]))

(defn- wrap-prop-value [^String k ^String v]
  (let [sep (System/getProperty "path.separator")]
    (when-> v (and (.contains v sep) (not (.startsWith v "http")))
            (->> (str/split v (re-pattern sep))
                 (filter (complement empty?))
                 (map #(str "<b>" % "</b>"))
                 (str/join "<br/>")))))

(defn- map->info-bytype [k]
  (let [m (case k
            :env (System/getenv)
            :prop (System/getProperties)
            [])]
    (map->kv-pairs m wrap-prop-value)))

(def memo-map->info (memoize/lu map->info-bytype :lu/threshold 3))

(defn- system-info []
  (let [^Runtime runtime (Runtime/getRuntime)]
    {:pid (runtime/pid)
     :available-processors (.availableProcessors runtime)
     :max-memory (.maxMemory runtime)
     :free-memory (.freeMemory runtime)
     :total-memory (.totalMemory runtime)}))

(defhandler env [req]
  (view "admin/envinfo"
        {:req (for [[k v] req] {:key k :value v})
         :env (memo-map->info :env)
         :prop (memo-map->info :prop)
         :system (map->kv-pairs (system-info))}))

(defhandler server-time []
  (json-success-message "" {:server_time (moment-format)}))

(defroutes admin-routes
  (GET "/admin/envinfo" [] env)
  (GET "/admin/server-time" [] server-time)
  (GET "/admin/server-time/polling" [] server-time))
