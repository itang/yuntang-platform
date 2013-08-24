(ns yuntang.layout.handlers.welcome 
  (:require [clojure.string :as str]
            [cljwtang :refer :all]
            [yuntang.user.core :refer [user-logined?]]))

(defhandler welcome [req]
  (view "welcome" ))

(defhandler dashboard [req]
  (view "dashboard"))

(defroutes welcome-routes
  (GET "/" req (if (user-logined?) dashboard welcome)))
