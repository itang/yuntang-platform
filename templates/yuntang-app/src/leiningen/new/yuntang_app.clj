(ns leiningen.new.yuntang-app
  (:require [leiningen.new.templates :refer [renderer name-to-path year ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "yuntang-app"))

(defn yuntang-app
  "yuntang app template"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)
              :year (year)}]
    (main/info "Generating fresh 'lein new' yuntang-app project.")
    (->files data
             [".gitignore"  (render "gitignore" data)]
             ["project.clj" (render "project.clj" data)]
             ["README.md"   (render "README.md" data)]
             ["src/lobos/config.clj" (render "lobos-config.clj" data)]
             ["src/lobos/migrations.clj" (render "lobos-migrations.clj" data)]
             ["src/{{sanitized}}/config.clj" (render "config.clj" data)]
             ["src/{{sanitized}}/module.clj" (render "module.clj" data)]
             ["src/{{sanitized}}/server.clj" (render "server.clj" data)]
             ["src/{{sanitized}}/views/snippets.clj" (render "snippets.clj" data)]
             ["src/{{sanitized}}/handlers/examples.clj" (render "examples.clj" data)]
             "src/{{sanitized}}/models"
             ["src-cljs/app/main.cljs" (render "main.cljs")]
             ["src-cljs/example/fileupload.cljs" (render "fileupload.cljs")]
             ["src-cljs/example/hello.cljs" (render "hello.cljs")]
             ["src-cljs/externs/angular.js" (render "angular.js")]
             ["src-cljs/externs/externs.js" (render "externs.js")]
             ["src-cljs/externs/jquery-1.8.js" (render "jquery-1.8.js")]
             ["resources/i18n-config.clj" (render "i18n-config.clj")]
             ["resources/log4j.properties" (render "log4j.properties" data)]
             ["resources/public/js/examples/ajax.js" (render "ajax.js")]
             "resources/public/img"
             ["resources/templates/examples/index.html" (render "templates/examples/index.html")]
             ["resources/templates/examples/_ajax.html" (render "templates/examples/_ajax.html")]
             ["resources/templates/examples/_ansj.html" (render "templates/examples/_ansj.html")]
             ["resources/templates/examples/_captcha.html" (render "templates/examples/_captcha.html")]
             ["resources/templates/examples/_cljs.html" (render "templates/examples/_cljs.html")]
             ["resources/templates/examples/_i18n.html" (render "templates/examples/_i18n.html")]
             ["resources/templates/examples/_match.html" (render "templates/examples/_match.html")]
             ["resources/templates/examples/fileupload.html" (render "templates/examples/fileupload.html")]
             
             ["prepare.sh" (render "prepare.sh" data)]
             ["prod.sh" (render "prod.sh" data)])))
