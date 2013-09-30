(ns yuntang.user.snippets
  (:require [cljwtang.lib :refer :all]
            [yuntang.modules.captcha.config :refer [captcha-enabled?]]
            [yuntang.user.core :refer [user-types]]))

(defsnippet user/captcha
  (captcha-enabled?)
  {})

(defsnippet user/type-options {:types user-types})
