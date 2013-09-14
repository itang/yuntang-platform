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
    {:get "" :fp-name "examples index"}
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

  (defhandler ajaxfileupload
    {:post "/ajax_form/ajaxfileupload"}
    [req]
    (let [p (:params req)
          file (:file p)
          filename (:filename file)
          ^java.io.File tempfile (:tempfile file)]
      (debug p)
      (when tempfile
        (upload-file tempfile filename))
      (json-success-message "" {:user (:username p)
                                :filesize (:size file)
                                :filename filename
                                :content-type (:content-type file)
                                :tempfile (some-> tempfile .getName)})))

  (defhandler ansj
    {:get "/ansj"}
    [source]
    (->> (ToAnalysis/paser source)
         str
         (json-success-message "操作完成")))

  (defhandler fileupload-index
    {:get "/fileupload" :fp-name "example fileupload"}
    []
    (view "examples/fileupload"
          {:noir-flash @*noir-flash*
           :noir-session @*noir-session*
           :mem @mem}))

  (defhandler fileupload
    {:post "/fileupload"}
    [req]
    (Thread/sleep (rand-nth (range 500 1600 100))) ;; mock
    (debug (:params req))
    (debug (pr-str (:multipart-params req)))
    (let [files (get-in req [:multipart-params "file"])]
      (debug "files" files)
      (if-not (or (map? files) (sequential? files))
        (json-error-message "no files")
        (let [fmap (if (sequential? files) files [files])]
          (session/flash-put! :msg (success-message "上传成功!"))
          (if (ajax? req)
            (json-success-message ""
                                  {:files (map #(dissoc % :tempfile) fmap)
                                   :user (get-in req [:params :user])})
            (redirect "/examples/fileupload"))))))

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
