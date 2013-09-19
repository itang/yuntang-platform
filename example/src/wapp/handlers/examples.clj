(ns wapp.handlers.examples
  (:import org.ansj.splitWord.analysis.ToAnalysis
           java.util.Date)
  (:require [clojure.core.match :refer [match]]
            [cljtang.core :refer :all]
            [coercer.core :refer [coerce]]
            [noir.session :refer [*noir-flash* *noir-session* mem] :as session]
            [taoensso.tower :as tower]
            [clj-captcha.core :refer [captcha-response-correc?]]
            [cljwtang.lib :refer :all]))

(with-routes examples-routes "/examples"

  (defhandler examples-index
    {:get "" :fp-name "examples index"
     :authenticated true}
    [req]
    (view "examples/index"
          {:foo (tower/t :example/foo)
           :locale (get-in req [:headers "accept-language"])}))

  (defhandler ajax
    {:get "/ajax"}
    [age]
    (json-success-message "Hello, World!"
                          {:age (inc (coerce age Integer)) :now (moment-format)}))
  
  (defhandler post-ajax
    {:post "/ajax"}
    [age]
    (json-success-message "Hello, World!"
                          {:age (inc (coerce age Integer)) :now (moment-format)}))

  (defhandler ajaxform
    {:post "/ajax_form/ajaxform"}
    [req]
    (json-success-message "" (:params req)))
  
  (defhandler ajaxsubmit
    {:post "/ajax_form/ajaxsubmit"}
    [req]
    (json-success-message "" (:params req)))

  (defhandler ansj
    {:get "/ansj"}
    [source]
    (->> (ToAnalysis/paser source)
         str
         (json-success-message "操作完成")))

  (defhandler fileupload-index
    {:get "/fileupload" :fp-name "example fileupload"
     :authenticated true}
    []
    (view "examples/fileupload"
          {:noir-flash @*noir-flash*
           :noir-session @*noir-session*
           :mem @mem}))

  (defhandler fileupload
    {:post "/fileupload"}
    [req]
    (Thread/sleep (rand-nth (range 500 1600 100))) ;; mock
    (let [files (multipart-files "file")
          _ (do #_()) ; 上传操作
          msg (if files
                 (success-message "上传成功!" (map :filename files))
                 (error-message "没有选择上传的文件!"))]
      (if (ajax? req)
        (json msg)
        (do 
          (flash-msg msg)
          (redirect "/examples/fileupload")))))
  
  (defhandler ajaxfileupload
    {:post "/ajax_form/ajaxfileupload"}
    [username req]
    (log-debug :params (:params req))
    (if-let [file (multipart-file "file")]
      (do 
        (upload-file! (:tempfile file) (:filename file))
        (json-success-message
          "上传成功!"
          (merge {:user username}
           (update-in file [:tempfile] #(some-> % .getName)))))
      (json-error-message "没有选择文件")))

  (defhandler va-captcha
    {:post "/va-captcha"}
    [captcha req]
    (let [correc? (captcha-response-correc? captcha)]
      (json-message correc?
                    (if correc? "验证码填写正确" "验证码填写有误")
                    {:is-response-correc correc?})))

  (defhandler match-demo
    {:get "/match"}
    []
    (->> (for [n (range 1 101)]
           (match [(mod n 3) (mod n 5)]
                  [0 0] [n "FizzBuzz"]
                  [0 _] [n "Fizz"]
                  [_ 0] [n "Buzz"]
                  :else [n "NON"]))
         (filter #(not= "NON" (second %)))
         (into {})
         (json-message true "" ))))
