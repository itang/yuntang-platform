(ns {{name}}.test.handler
  (:require [cljwtang.lib :as cljwtang])
  (:use clojure.test
        ring.mock.request
        {{name}}.server))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/_lib_version"))]
      (is (= (:status response) 200))
      (is (= (:body response) cljwtang/version))))
  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))
