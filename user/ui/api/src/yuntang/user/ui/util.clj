(ns yuntang.user.ui.util
  (:require [clojure.string :refer [trim]]))

(def ^:private cexample
  (str "abcdefghijklmnopqrstuvwxy"
       "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
       "0123456789"
       "-_@."))

(defn check-username-pattern [username]
  (when (not-every? #(.contains ^String cexample (str %)) username)
      "用户名由字母 数字 _ - @ . 组成, 如itang1"))
