(ns wapp.views.snippets
  (:require [cljtang.core :refer :all]))

(defn hello-snippet
  [a c]
  (str "Hello snippet!" (moment-format)))
