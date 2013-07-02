(ns yuntang.modules.common.ui.handlers.appconfig
  (:require [clojure.string :as str]
            [clojure.tools.logging :refer [debug info]]
            [compojure.core :refer :all]
            [cljtang.core :refer :all]
            [cljwtang.core :refer :all]
            [cljwtang.view :refer :all]
            [yuntang.modules.common.appconfig.core :as appconfigs]))

(defhandler index []
  (let [items (map #(assoc %
                      :created-at (format-date (:created_at %))
                      :updated-at (some-> (:updated_at %) format-date))
                (appconfigs/find-all))]
    (layout-view "modules/common/appconfig"
      {:appconfigs (maplist-with-no items)
       :size (count items)})))

(defroutes appconfig-routes
  (GET "/admin/appconfigs" [] index))
