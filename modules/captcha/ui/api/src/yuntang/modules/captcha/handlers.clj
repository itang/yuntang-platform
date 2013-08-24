(ns yuntang.modules.captcha.handlers
  (:import java.io.ByteArrayInputStream)
  (:require [cljwtang :refer :all]
            [clj-captcha.core :refer [captcha-challenge-as-jpeg]]))

(defhandler captcha[req]
  (let [bytes (captcha-challenge-as-jpeg)
        body (ByteArrayInputStream. bytes)
        length (count bytes)]
    (->> body
      (content-type "image/jpeg")
      (content-length (str length)))))

(defroutes captcha-routes
  (GET "/captcha" [] captcha))
