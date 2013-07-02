(ns yuntang.layout.client.wapp
  (:require [yuntang.layout.client.controllers :refer [ClientMomentTimeCtrl]]))

(def wapp
  (doto (.module js/angular "wapp" (array))
    (.config (array "$interpolateProvider"
               (fn [$interpolateProvider]
                 (doto $interpolateProvider
                   (.startSymbol "[[")
                   (.endSymbol "]]")))))))

(defn add-controller [name controller]
  (.controller wapp name controller))

;; buildin controller
(add-controller "ClientMomentTimeCtrl" ClientMomentTimeCtrl)
