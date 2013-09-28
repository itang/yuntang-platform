(defproject yuntang-platform "0.1.0-SNAPSHOT"
  :description "clojure web platform"
  :url "http://yuntang-platform.deftype.com"
  :plugins [[lein-sub "0.2.4"]]
  :sub ["user"
        "admin"
        "layout"
        "modules"
        "example"]
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"})
