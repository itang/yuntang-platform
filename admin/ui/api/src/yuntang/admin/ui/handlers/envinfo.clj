(ns yuntang.admin.ui.handlers.envinfo
  (:require [clojure.string :as str]
            [cljtang.core :refer :all]
            [clojure.core.memoize :as memoize]
            [compojure.core :refer :all]
            [noir.response :refer [json]]
            [cljwtang.core :refer :all]
            [cljwtang.view :refer :all]))

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

(defhandler env [req]
  (layout-view "admin/envinfo" 
               {:req (for [[k v] req] {:key k :value v})
                :env (memo-map->info :env)
                :prop (memo-map->info :prop)}))

(defhandler server-time []
  (json-success-message "" {:server_time (moment-format)}))

(defroutes envinfo-routes
  (GET "/admin/envinfo" [] env)
  (GET "/admin/server-time" [] server-time)
  (GET "/admin/server-time/polling" [] server-time))
