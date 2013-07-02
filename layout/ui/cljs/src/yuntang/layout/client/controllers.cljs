(ns yuntang.layout.client.controllers
  (:require [yuntang.layout.client.util :refer [moment-format]]))

(defn ClientMomentTimeCtrl [$scope $timeout]
  (aset $scope "moment" (moment-format))
  (.$watch $scope "moment"
    (fn [moment]
      ($timeout #(aset $scope "moment" (moment-format)) 2000))))

(aset ClientMomentTimeCtrl "$inject" (array "$scope" "$timeout"))
