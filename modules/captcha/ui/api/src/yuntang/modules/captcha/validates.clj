(ns yuntang.modules.captcha.validates
  (:require [cljwtang.lib :refer :all]
            [clj-captcha.core :refer [captcha-response-correc?]]
            [yuntang.modules.captcha.config :refer :all]))

(defn validate-captcha [& [err-msg]]
  (validate-rule (or (not (captcha-enabled?)) (captcha-response-correc?))
                 [:captcha (or err-msg "验证码填写有误")]))
