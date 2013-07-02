(ns yuntang.layout.client.core
  (:require [jayq.core :refer [$ show hide]]
            [jayq.util :refer [log]]
            [cljstang.core :refer [ends-with]]
            [cljstang.json :as json]))

(defn- parse-ajax-result [xhr]
  (let [status (.-status xhr)
        result (try 
                 (json/parse (.-responseText xhr))
                 (catch js/Object e nil))
        success? (and (not= 500 status) result (.-success result))]
    {:status status :result result :success? success?}))

(defn- request-from-local? []
  (not= -1
        (.indexOf (.-href js/location) "localhost")))

(defn- trace [event xhr settings & [exception]]
  (->> (js-obj
        "event.type" (.-type event)
        "event.which" (.-which event)
        "settings.url" (.-url settings)
        "xhr.status" (.-status xhr)
        "xhr.statusText" (.-statusText xhr)
        "xhr.responseType" (.-responseType xhr)
        "xhr.responseText" (.-responseText xhr)
        "exception" (.toString (if exception exception "")))
       (json/stringify)
       (str "-trace ajax: ")
       log))

(defn- ajax-complete [event xhr settings & [exception]]
  (let [url (.-url settings)]
    (if-not (ends-with url "/polling")
      (let [{:keys [status result success?]} (parse-ajax-result xhr)
            title (str "操作" (if success? "成功! " "失败! "))]
        (.pnotify js/$
          (js-obj
            "title" title
            "text" (.-message result)
            "type" (.-type result))))))
    (if (request-from-local?)
      (trace event xhr settings exception)))

(.ajaxComplete (js/$ js/document)
  (fn [event xhr settings]
    (hide ($ :#ajax-preload))
    (ajax-complete event xhr settings)))

(.ajaxSend (js/$ js/document)
  #(show ($ :#ajax-preload)))
