(ns lobos.migrations
  (:refer-clojure :exclude [alter drop
                            bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]]
               core schema config))
  (:use cljwtang.tools.lobos-helpers))

;; TODO auto scan packages and require
(require 'yuntang.user.migrations)

(defmigration add-posts-table
  (up [] (create
          (tbl :posts
               (varchar :title 200 :unique)
               (text :content)
               (refer-to :users))))
  (down [] (drop (table :posts))))

(defmigration add-comments-table
  (up [] (create
          (tbl :comments
               (text :content)
               (refer-to :users)
               (refer-to :posts))))
  (down [] (drop (table :comments))))

;; 应用运行时配置
(defmigration add-appconfig-table
  (up [] (create
          (tbl :appconfigs
               (varchar :key 100 :unique)
               (varchar :value 1000)
               (check :key (> (length :key) 1)))))
  (down [] (drop (table :appconfigs))))
