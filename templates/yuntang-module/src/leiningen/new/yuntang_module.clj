(ns leiningen.new.yuntang-module
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "yuntang-module"))

(defn yuntang-module
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' yuntang-module project.")
    (->files data
             ["src/{{sanitized}}/foo.clj" (render "foo.clj" data)])))
