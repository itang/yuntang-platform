(ns yuntang.modules.captcha.ui.handlers
  (:import java.io.ByteArrayInputStream)
  (:require [compojure.core :refer :all]
            [noir.response :refer [content-type]]
            [cljwtang.core :refer :all]
            [clj-captcha.core :refer [captcha-challenge-as-jpeg]]
            [cljwtang.response :refer [content-length]]))

(defhandler captcha[req]
  (let [bytes (captcha-challenge-as-jpeg)
        body (ByteArrayInputStream. bytes)
        length (count bytes)]
    (->> body
      (content-type "image/jpeg")
      (content-length (str length)))))

(defroutes captcha-routes
  (GET "/captcha" [] captcha))
