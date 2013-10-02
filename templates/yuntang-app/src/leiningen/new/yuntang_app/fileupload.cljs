(ns example.fileupload
  (:require [jayq.core :refer [$ bind]]))

(defn- ajaxsubmit-handler [event]
  (.ajaxSubmit 
    ($ :#f-fileuplaod)
    (js-obj
      "dataType" "json"
      "success"  (fn [result] 
                   (js/alert (str "来自服务器端:"  (.stringify js/JSON result)))))))

(defn ^:export init []
  (-> ($ :#btn-ajaxsubmit)
    (bind :click ajaxsubmit-handler)))
