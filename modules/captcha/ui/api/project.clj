(defproject yuntang-platform/yuntang-modules-captcha-ui-api "0.1.0-SNAPSHOT"
  :description "clojure web app libs"
  :url "http://captcha.deftype.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-captcha "0.1"]
                 [cljwtang "0.1.0-SNAPSHOT"]]
  :profiles {:dev {:plugins [[codox "0.6.4"]
                             [lein-pprint "1.1.1"]
                             [lein-checkall "0.1.1"]]}
             :1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :1.5 {:dependencies [[org.clojure/clojure "1.5.1"]]}}
  :aliases {"run-tests" ["with-profile" "1.4:1.5" "test"]}
  :global-vars {*warn-on-reflection* true}
  :injections [(require 'clojure.pprint)]
  :min-lein-version "2.0.0"
  :pom-addition [:developers [:developer
                              [:id "itang"]
                              [:name "唐古拉山"]
                              [:url "http://www.itang.me"]
                              [:email "live.tang@gmail.com"]]])
