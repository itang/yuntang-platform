(ns {{name}}.views.snippets
  (:require [cljtang.lib :refer :all]))

(defn hello-snippet
  [a c]
  (str "Hello snippet!" (moment-format)))
