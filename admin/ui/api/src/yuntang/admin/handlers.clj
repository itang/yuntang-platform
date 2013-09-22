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

(defn- render-server-time []
  (json-success-message "" {:server_time (moment-format)}))

(with-routes admin-routes {:path "/admin" :perm "admin"}
  (defhandler env
    "环境信息"
    {:get "/envinfo"
     :fp-name "环境信息"}
    [req]
    (view "admin/envinfo"
          {:env (memo-map->info :env)
           :prop (memo-map->info :prop)
           :system (map->kv-pairs (system-info))
           :sub-modules (app-sub-modules)}))
  
  (defhandler debug
    "调试页面"
    {:get "/debug"
     :fp-name "调试"}
    [req]
    (view "admin/debug"
          {:req (for [[k v] req] {:key k :value v})}))

  (defhandler server-time
    {:get "/server-time"}
    []
    (render-server-time))

  (defhandler server-time-polling
    {:get "/server-time/polling"}
    []
    (render-server-time)))
