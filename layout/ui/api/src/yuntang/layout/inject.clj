(ns yuntang.layout.inject
  (:require [cljwtang.datatype :refer [menu-tree]]))

(def ^:dynamic the-menus [])

(defn inject-menus [& menus]
  (let [ms (menu-tree menus)]
    (alter-var-root #'the-menus (constantly ms))))
