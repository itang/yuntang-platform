(ns yuntang.layout.handlers
  (:require [clojure.string :as str]
            [cljwtang.lib :refer :all]))

(defhandler welcome [req]
  (view "welcome" ))

(defhandler dashboard [req]
  (view "dashboard"))

(defroutes layout-routes
  (GET "/" req (if (*user-logined?-fn*) dashboard welcome)))
