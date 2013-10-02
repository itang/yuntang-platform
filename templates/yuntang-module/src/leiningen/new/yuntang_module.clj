(ns leiningen.new.yuntang-module
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "yuntang-module"))

(defn yuntang-module
  "yuntang module template"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' yuntang-module project.")
    (->files data
             [".gitignore"  (render "gitignore" data)]
             ["project.clj" (render "project.clj" data)]
             ["README.md"   (render "README.md" data)]
             ["src/{{sanitized}}/bootstrap.clj" (render "bootstrap.clj" data)]
             ["src/{{sanitized}}/config.clj" (render "config.clj" data)]
             ["src/{{sanitized}}/handlers.clj" (render "handlers.clj" data)]
             ["src/{{sanitized}}/module.clj" (render "module.clj" data)]
             ["src/{{sanitized}}/snippets.clj" (render "snippets.clj" data)]
             ["src/{{sanitized}}/models.clj" (render "models.clj" data)]
             ["src/{{sanitized}}/migrations.clj" (render "migrations.clj" data)]
             "src-cljs"
             "resources/public/js/{{name}}"
             "resources/public/img/{{name}}"
             "resources/public/css/{{name}}"
             ["resources/templates/{{name}}/index.html" (render "index.html")])))
