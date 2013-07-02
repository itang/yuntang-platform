(ns yuntang.user.ui.snippets
  (:require [cljwtang.view :refer :all]
            [yuntang.user.ui.config :refer [captcha-enabled?]]
            [yuntang.user.core :refer [user-types]]))

(defsnippet user/captcha
  (captcha-enabled?)
  {})

(defsnippet user/type-options {:types user-types})
