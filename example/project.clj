(defproject yuntang-platform/example "0.1.0-SNAPSHOT"
  :description "Clojure Web Platform example"
  :url "http://wapp.itang.me"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [yuntang-platform/yuntang-modules-common-ui-res "0.1.0-SNAPSHOT"]
                 [yuntang-platform/yuntang-user-ui-res "0.1.0-SNAPSHOT"]
                 [yuntang-platform/yuntang-admin-ui-res "0.1.0-SNAPSHOT"]
                 [yuntang-platform/yuntang-layout-ui-res "0.1.0-SNAPSHOT"]
                 [yuntang-platform/yuntang-modules-captcha-ui-api "0.1.0-SNAPSHOT"]
                 [clj-captcha "0.1.1-SNAPSHOT"]
                 [ansj_seg/ansj_seg "1.0"]]
  :repositories [["itang-repo" "http://itang.github.io/maven-repo"]]
  :profiles {:dev {:plugins [[lein-ancient "0.4.4"]  ;$ lein ancient > check your project for outdated dependencies and plugins
                             [codox "0.6.4"]         ;$ lein doc
                             [lein-ring "0.8.7"]     ;$ lein ring server
                             [lein-pprint "1.1.1"]   ;$ lein pprint | lein with-profile 1.4 pprint
                             [lein-cljsbuild "0.3.2"];$ lein cljsbuild [once auto clean repl-listen repl-rhino]
                             [lein-environ "0.4.0"]
                             [lein-localrepo "0.5.2"]
                             [lein-checkall "0.1.1"]]
                   :dependencies [[ring-mock "0.1.5"]
                                  [org.clojure/clojurescript "0.0-1859"]]}
             :1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :1.5 {:dependencies [[org.clojure/clojure "1.5.1"]]}}
  :aliases {"run-tests" ["with-profile" "1.4:1.5" "test"]
            "migrate" ["run" "-m" "cljwtang.tools.migrate"]
            "dev" ["run" "-m" "cljwtang.tools.dev"]
            "http-kit" ["run"]}
  :main ^{:skip-aot true} wapp.server
  :ring {:handler wapp.server/app
         :init wapp.server/init
         :auto-refresh? true}
  :jvm-opts ["-Xms392m" "-Xmx1g"]
  ;;:bootclasspath true
                                        ;:hooks [leiningen.cljsbuild]
  ;; Forms to prepend to every form that is evaluated inside your project.
  ;; Allows working around the Gilardi Scenario: http://technomancy.us/143
  :injections [(require 'clojure.pprint)]
  ;; Emit warnings on all reflection calls.
                                        ;:warn-on-reflection true
  ;; include arbitrary xml in generated pom.xml file
  :pom-addition [:developers [:developer [:name "itang"]]]
  :min-lein-version "2.0.0"
  :cljsbuild {
              :builds {
                       :app-debug
                       {
                        :source-paths ["src-cljs"],
                        :compiler {
                                   :pretty-print true,
                                   :output-to "resources/public/js/app-debug.js",
                                   :optimizations :simple #_(:whitespace),
                                   :externs ["src-cljs/externs/jquery-1.8.js",
                                             "src-cljs/externs/angular.js"
                                             "src-cljs/externs/externs.js"]},
                        :jar true}
                       :app
                       {
                        :source-paths ["src-cljs"],
                        :compiler {
                                   :pretty-print false,
                                   :output-to "resources/public/js/app.js",
                                   :optimizations :advanced,
                                   :externs ["src-cljs/externs/jquery-1.8.js",
                                             "src-cljs/externs/angular.js"
                                             "src-cljs/externs/externs.js"]},
                        :jar true}}}
  )
