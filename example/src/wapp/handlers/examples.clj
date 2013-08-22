(ns wapp.handlers.examples
  (:import org.ansj.splitWord.analysis.ToAnalysis
           java.util.Date)
  (:require [clojure.core.match :refer [match]]
            [clojure.tools.logging :as log]
            [cljtang.core :refer :all]
            [coercer.core :refer [coerce]]
            [compojure.core :refer :all]
            [noir.response :refer [json redirect]]
            [noir.session :refer [*noir-flash* *noir-session* mem]:as session]
            [taoensso.tower :as tower]
            [cljwtang.core :refer :all]
            [cljwtang.view :refer :all]
            [cljwtang.request :refer [ajax?]]
            [cljwtang.response :refer [html]]
            [cljwtang.utils.upload :refer [upload-file]]
            [clj-captcha.core :refer [captcha-response-correc?]]))

(defhandler index [req]
  (view "examples/index"
               {:foo (tower/t :example/foo)
                :locale (get-in req [:headers "accept-language"])}))

(defhandler ajax [age]
  (json-message true
                "Hello, World!"
                {:age (inc (coerce age Integer)) :now (moment-format)}))

(defhandler ajaxform [req]
  (json-success-message "" (:params req)))

(defhandler ajaxfileupload [req]
  (let [p (:params req)
        file (:file p)
        filename (:filename file)
        ^java.io.File tempfile (:tempfile file)]
    (log/debug p)
    (when tempfile
      (upload-file tempfile filename))
    (json-success-message "" {:user (:username p)
                              :filesize (:size file)
                              :filename filename
                              :content-type (:content-type file)
                              :tempfile (some-> tempfile .getName)})))

(defhandler ansj [source]
  (->> (ToAnalysis/paser source)
    str
    (json-success-message "操作完成")))

(defhandler fileupload-index []
  (view "examples/fileupload"
               {:noir-flash @*noir-flash*
                :noir-session @*noir-session*
                :mem @mem}))

(defhandler fileupload [req]
  (Thread/sleep (rand-nth (range 500 1600 100))) ;; mock
  (log/debug (:params req))
  (log/debug (pr-str (:multipart-params req)))
  (let [files (get-in req [:multipart-params "file"])]
    (log/debug "files" files)
    (if-not (or (map? files) (sequential? files))
      (json-error-message "no files")
      (let [fmap (if (sequential? files) files [files])]
        (session/flash-put! :msg (success-message "上传成功!"))
        (if (ajax? req)
          (json-success-message ""
                                {:files (map #(dissoc % :tempfile) fmap)
                                 :user (get-in req [:params :user])})
          (redirect "/examples/fileupload"))))))

(defn ft []
  (flash-msg "flash msg")
  (redirect "/examples/show"))

(defn show []
  (str "msg:" (flash-msg) "<a href='/examples/ft'>++</a>"))

(defhandler va-captcha [captcha req]
  (let [correc? (captcha-response-correc? captcha)]
    (json-message correc?
                  (if correc? "验证码填写正确" "验证码填写有误")
                  {:is-response-correc correc?})))

(def v-def "hello")
(def v-delay (delay "hello"))

;; 7789.42 [#/sec] (mean)
(defhandler perf-def []
  (html v-def))

;; ab -n 100000 -c 10 -k => 7576.80 [#/sec]
(defhandler perf-delay []
  (html @v-delay))

(defhandler match-demo []
  (->> (for [n (range 1 101)]
         (match [(mod n 3) (mod n 5)]
                [0 0] [n "FizzBuzz"]
                [0 _] [n "Fizz"]
                [_ 0] [n "Buzz"]
                :else [n "NON"]))
    (filter #(not= "NON" (second %)))
    (into {})
    (json-message true "" )))

(defroutes examples-routes
  (context "/examples" []
    (GET "/perf/def" [] perf-def)
    (GET "/perf/delay" [] perf-delay)
    (GET "/" [] index)
    (GET "/ft" [] ft)
    (GET "/show" [] show)
    (GET "/ajax" [] ajax)
    (POST "/ajax_form/ajaxform" [] ajaxform)
    (POST "/ajax_form/ajaxsubmit" [] ajaxform)
    (POST "/ajax_form/ajaxfileupload" [] ajaxfileupload)
    (GET "/ansj" [] ansj)
    (GET "/fileupload" [] fileupload-index)
    (POST "/fileupload" [] fileupload)
    (POST "/va-captcha" [] va-captcha)
    (GET "/match" [] match-demo)))
