(ns yuntang.layout.handlers
  (:require [clojure.string :as str]
            [cljwtang :refer :all]
            [yuntang.user.core :refer [user-logined?]]))

(defhandler welcome [req]
  (view "welcome" ))

(defhandler dashboard [req]
  (view "dashboard"))

(defroutes layout-routes
  (GET "/" req (if (user-logined?) dashboard welcome)))
