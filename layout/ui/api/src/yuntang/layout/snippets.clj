(ns yuntang.layout.snippets
  (:import java.util.Calendar)
  (:require [cljtang.lib :refer :all]
            [hiccup.core :as hiccup]
            [cljwtang.lib :refer :all]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; common

(def start-time (moment))

(defsnippet flash-msgs
  (flash-msg)
  (let [msg ctx
        data (:data msg)
        messages (if (map? data)
                   (-> (:data msg) vals flatten)
                   [])
        messages (if (empty? messages)
                   (conj messages (:message msg))
                   messages)
        text (hiccup/html [:ul] (for [m messages] [:li m]))]
    {:type (some-> (:type msg) name)
     :messages messages
     :text text}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; main

;; 主导航
(defsnippet main/nav {:menus (app-menus)})

;; 主区域头部 (包括当前位置等)
(defsnippet main/header {})

;; 通知区域
(defsnippet main/notifications {})

;; 消息区域
(defsnippet main/messages {})

;; 用户信息区域
(defsnippet main/user-info {:user (*current-user-fn*)})

;; footer区域
(defsnippet main/footer
  (let [start-year (*app-config-fn* :plaform.start-year 2013)
        end-year (.get (Calendar/getInstance) Calendar/YEAR)
        years (str start-year
                (when-not (= end-year start-year) (str "-" end-year)))
        name (*app-config-fn* :platform.name)
        description (*app-config-fn* :platform.fullname "基于Clojure的Web平台")]
    {:years years
     :name name
     :description description}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; page's snippets

;; 运行时信息
(defsnippet runtime-info
  {:mode run-mode
   :start-time start-time
   :server-time (moment)})
