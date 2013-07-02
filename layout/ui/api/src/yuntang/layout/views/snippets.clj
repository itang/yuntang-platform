(ns yuntang.layout.views.snippets
  (:import java.util.Calendar)
  (:require [cljtang.core :refer :all]
            [hiccup.core :refer [html]]
            [noir.session :as session]
            [clj-pretty-format.core :refer [pretty-format]]
            [cljwtang.core :refer :all]
            [cljwtang.view :refer :all]
            [yuntang.user.core :refer [current-user]]
            [yuntang.layout.inject :as inject]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; common

(def start-time (java.util.Date.))

(defsnippet flash-msgs
  (flash-msg)
  (let [msg ctx
        messages (-> (:data msg) vals flatten)
        messages (if (empty? messages)
                   (conj messages (:message msg))
                   messages)
        text (html [:ul] (for [m messages] [:li m]))]
    {:type (some-> (:type msg) name)
     :messages messages
     :text text}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; main

;; 主导航
(defsnippet main/nav {:menus inject/the-menus})

;; 主区域头部 (包括当前位置等)
(defsnippet main/header {})

;; 通知区域
(defsnippet main/notifications {})

;; 消息区域
(defsnippet main/messages {})

;; 用户信息区域
(defsnippet main/user-info {:user (current-user)})

;; footer区域
(defsnippet main/footer
  (let [start-year (app-config :plaform.start-year 2013)
        end-year (.get (Calendar/getInstance) Calendar/YEAR)
        years (str start-year
                (when-not (= end-year start-year) (str "-" end-year)))
        name (app-config :platform.name)
        description (app-config :platform.fullname "基于Clojure的Web平台")]
    {:years years
     :name name
     :description description}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; page's snippets

;; 运行时信息
(defsnippet runtime-info
  {:mode run-mode
   :start-time (format-date start-time)
   :start-time-pretty (pretty-format start-time)
   :server-time (moment-format)})
