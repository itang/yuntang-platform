(ns {{name}}.migrations
  (:refer-clojure :exclude [alter drop
                            bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]] core schema))
  (:use cljwtang.tools.lobos-helpers))


#_(defmigration add-table1-table
  (up [] (create
          (tbl :permits
               (varchar :name 200 :unique)
               (varchar :description 500))))
  (down [] (drop (table :permits))))
