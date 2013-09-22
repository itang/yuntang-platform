(ns yuntang.admin.module
  (:require [cljwtang.lib :refer :all]
            [yuntang.admin.handlers :refer :all]))

(def fp-admin-envinfo 
  (new-funcpoint {:name "admin.envinfo"
                  :url "/admin/envinfo"
                  :perm "admin.envinfo"}))

(def module
  (new-ui-module 
    {:name "admin"
     :routes [admin-routes]
     :fps [env-fp debug-fp]
     :menus (maps->menus
              [{:id "admin"
                :name "系统管理"
                :attrs {:classname "icon-gift"}}
               {:name "运行环境"
                :funcpoint fp-admin-envinfo
                :attrs {:classname "icol-user"}
                :parent "admin"}
               {:name "调试"
                :funcpoint debug-fp
                :attrs {:classname "icol-user"}
                :parent "admin"
                :sort 1000}])}))
