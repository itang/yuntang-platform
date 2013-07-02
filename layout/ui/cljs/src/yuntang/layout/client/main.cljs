(ns yuntang.layout.client.main
  (:require [jayq.core :refer [$ bind attr html parent remove-class next]]
            [jayq.util :refer [log]]
            [cljstang.core :refer [ends-with]]))

(defn- text-from [ehtml]
  (let [p (.lastIndexOf ehtml ">")]
    (.substring ehtml (inc p))))

(defn- highlight-current-location
  "当前位置相关的处理: 高亮当前菜单, 当前位置, 改变title"
  []
  (let [curr-href (.-href js/location)
        topmenu-li-sel "#sidebar #navigation > ul > li"
        smenu-li-sel (str topmenu-li-sel " > ul > li")
        smenu-a-sel (str smenu-li-sel " > a")
        $e ($ smenu-a-sel)
        num (count $e)]
    ;; 依次匹配所有Sub Menu
    (loop [i 0]
      (if (< i num)
        (let [$ce (nth $e i)
              href (attr $ce "href")]
          (if (ends-with curr-href href)
            ;; 匹配上
            (let [$topmenu (-> $ce parent parent parent)
                  ehtml (html $ce)
                  $title ($ "head title")
                  title (html $title)]
              ;; 设置高亮Menu
              (remove-class ($ (str smenu-li-sel ".active")) "active")
              (attr (parent $ce) "class" "active")
              (remove-class ($ (str topmenu-li-sel ".active")) "active")
              (attr $topmenu "class" "active")
              ;; 设置当前位置
              (html ($ "#main-header .sub-menu") ehtml)
              ;; 设置web title
              (html $title (str (text-from ehtml)  " - " title)))
            (recur (inc i))))))
  
    (.updateSidebarNav (.-template js/$)
      (.first ($ (str topmenu-li-sel ".active"))
      true))))

(defn ^:export init []
  (highlight-current-location))
