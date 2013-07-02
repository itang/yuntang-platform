(ns example.hello
  (:require [jayq.core :refer [$ html bind]]))

;;(js/alert "Hello from ClojureScript!")

(defn ^:export hello []
  (js/alert "Hello, ClojureScript!"))

(defn ^:export init []
  (-> ($ :#cljs-info)
    (html "<b>From ClojureScript</b>"))
  (-> ($ :#btn-sayhello)
    (bind :click hello)))