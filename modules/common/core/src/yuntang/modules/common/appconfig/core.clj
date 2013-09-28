(ns yuntang.modules.common.appconfig.core
  (:require [korma.core :refer :all]
            [clojure.core.memoize :as memoize]
            [cljtang.lib :refer :all]
            [cljwtang.utils.env :refer [env-config]]))

;; 定义应用配置实体
(defentity appconfigs)

(def ^:private key-values
  (fn-*
    (->> (select appconfigs)
      (map (fn [record] 
             [(:key record)
              ;; unsafe?
              (read-string (:value record))]))
      (into {}))))

(def ^:private CACHE-SIZE 1)

(def ^:private key-values-cache (memoize/lu key-values :lu/threshold CACHE-SIZE))

;;;; PUBLIC API
(defn fresh-cache []
  (memoize/memo-clear! key-values-cache)
  (key-values-cache :all)) ;; pre-reload

(defn find-by-key [^String key]
  (first (select appconfigs
                 (where {:key key}))))

(defn find-all []
  (select appconfigs))

(defn rt-config 
  "运行时配置, 默认空值(nil)不会写入库"
  [key & [default-value]]
  (let [key (name key)
        value (get (key-values-cache :all) key)]
    (if-nil value
      (when-not-nil default-value
        (insert appconfigs
          (values {:key key :value (pr-str default-value)}))
        (fresh-cache)
        default-value)
      value)))

(defn app-config
  "应用配置, 先读取运行时配置，如果不存在，则尝试环境配置，还不存在，则写入运行时配置并返回值"
  ([key] 
    (app-config key nil))
  ([key default-value]
    (let [value (or (rt-config key) (env-config key))]
      (if-nil value
         (rt-config key default-value)
         value))))
