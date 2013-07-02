(ns yuntang.modules.hbs.core
  (:require [cljwtang.template.core :refer [TemplateEngine]]
            [hbs.core :as hbs]))

(deftype HbsTemplateEngine
  [the-name tags-map template-path-prefix template-path-suffix] 
  TemplateEngine
  (name [_]
    the-name)
  (render-string [_ s data]
    (hbs/render s (merge @tags-map data)))
  (render-file [_ path data]
    (hbs/render-file path (merge @tags-map data)))
  (regist-tag [_ k v]
    (let [lambad
          (reify
           com.github.jknack.handlebars.Lambda
           (apply [this scope template]
            (v (.text ^com.github.jknack.handlebars.Template template))))]
       (swap! tags-map assoc k lambad)))
  (clear-cache! [_]
    (.. ^com.github.jknack.handlebars.Handlebars hbs/*hbs* getCache clear)))

(defn new-hbs-template-engine []
  (let [template-path-prefix "/templates"
        template-path-suffix ".mustache"]
    (hbs/set-template-path! template-path-prefix template-path-suffix)
    (HbsTemplateEngine.
      "handlebars" (atom {}) template-path-prefix template-path-suffix)))
