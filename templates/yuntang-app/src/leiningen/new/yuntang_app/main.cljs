(ns app.main
  (:require [yuntang.layout.client.core]
            [yuntang.layout.client.main :as layout]
            [yuntang.layout.client.wapp :as wapp]))

(defn ^:export init []
  (layout/init))
