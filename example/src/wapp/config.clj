(ns wapp.config
  (:require [clojure.string :as str]
            [korma.db :refer [defdb h2 postgres]]
            [cljwtang.core :refer :all]))

(def ^:private postgres-db
  (postgres {:db "korma"
             :user "korma"
             :password "kormapass"
             ;;OPTIONAL KEYS
             :host "myhost"
             :port "4567"
             :delimiters "" ;; remove delimiters
             :naming { ;; set map keys to lower
                      :keys str/lower-case
                      ;; but field names are upper
                      :fields str/upper-case}}))

(def ^:private h2-db-dev
  (h2 {:subname "~/wapp_dev;AUTO_SERVER=TRUE"
       :user "sa"
       :password ""}))

(def ^:private h2-db-prod
  (h2 {:subname "~/wapp_prod;AUTO_SERVER=TRUE"
       :user "sa"
       :password ""}))

(def ^{:doc "数据库配置"}
  db-config
  (if prod-mode?
    #_( postgres-db) h2-db-prod
    h2-db-dev))
