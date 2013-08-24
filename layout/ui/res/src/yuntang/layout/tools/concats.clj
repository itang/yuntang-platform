(ns yuntang.layout.tools.concats
  (:require [cljtang.core :refer :all]
            [cljtang.io :refer [concatenating]]))

(defn- full-path [prefix src]
  (if (.startsWith ^String src "http")
    src
    (str prefix src)))

(defn- js-comment [comment]
  (str "/**\n" comment "\n*/"))

(defn- gh [uri]
  (str "http://raw.github.com/" uri))

(defn- ghs [uri]
  (str "https://raw.github.com/" uri))

(defn- h [url]
  (str "http://" url))

(def jsbase-dest "resources/public/js/base.js")

(def jslib-dest "resources/public/js/lib.js")

(def jslayout-lib-dest "resources/public/js/layout-lib.js")

(def angular-version "1.0.6")

(def jsbase-src 
  (map 
    (partial full-path "resources/public/")
    [#_"http://code.jquery.com/jquery-2.0.0.min.js"
     #_("http://code.jquery.com/jquery-1.8.3.min.js")
     "http://code.jquery.com/jquery-migrate-1.2.1.min.js"
     "https://raw.github.com/bestiejs/lodash/v1.2.1/dist/lodash.min.js"
     "http://malsup.github.com/jquery.form.js"
     (ghs "kflorence/jquery-deserialize/master/dist/jquery.deserialize.min.js")
     #_("libs/jquery.cookies.2.2.0.min.js")
     (ghs "mathiasbynens/jquery-placeholder/master/jquery.placeholder.js")
     #_("mooncake/assets/js/libs/jquery.placeholder.min.js")
   #_("https://raw.github.com/pixelmatrix/uniform/master/jquery.uniform.min.js")
     "mooncake/plugins/uniform/jquery.uniform.min.js"
     "https://raw.github.com/sciactive/pnotify/master/jquery.pnotify.min.js"
     (h "ajax.aspnetcdn.com/ajax/jquery.validate/1.11.0/jquery.validate.min.js")
     #_("mooncake/plugins/validate/jquery.validate.min.js")
     "https://raw.github.com/timrwood/moment/2.0.0/min/moment.min.js"
     "mooncake/bootstrap/js/bootstrap.min.js"
     (str "http://ajax.googleapis.com/ajax/libs/angularjs/" angular-version
       "/angular.min.js")
     (str "http://ajax.googleapis.com/ajax/libs/angularjs/" angular-version
       "/angular-resource.min.js")
     "js/wapp.js"]))

(def jslib-src 
  (map 
    (partial full-path "resources/public/mooncake/")
    ["assets/js/libs/jquery.mousewheel.min.js"
     "plugins/flot/jquery.flot.min.js"
     "plugins/flot/plugins/jquery.flot.tooltip.min.js"
     "plugins/flot/plugins/jquery.flot.pie.min.js"
     "plugins/flot/plugins/jquery.flot.resize.min.js"
     "plugins/sparkline/jquery.sparkline.min.js"
     "plugins/ibutton/jquery.ibutton.min.js"

     "custom-plugins/circular-stat/circular-stat.min.js"

     "http://code.jquery.com/ui/1.10.2/jquery-ui.min.js"
     #_("assets/jui/js/jquery-ui-1.9.2.min.js")
     "assets/jui/jquery-ui.custom.min.js"
     "assets/jui/timepicker/jquery-ui-timepicker.min.js"
     "assets/jui/jquery.ui.touch-punch.min.js"

     "plugins/fullcalendar/fullcalendar.min.js"

     "plugins/datatables/jquery.dataTables.min.js"
     "plugins/datatables/TableTools/js/TableTools.min.js"
     "plugins/datatables/dataTables.bootstrap.js"]))

(def jslayout-lib-src
  (map 
    (partial full-path "resources/public/mooncake/")
    ["custom-plugins/bootstrap-fileinput.min.js"
     "custom-plugins/bootstrap-inputmask.min.js"
     
     "plugins/uniform/jquery.uniform.min.js"
     
     "assets/js/template.js"
     "assets/js/setup.js"
     "assets/js/customizer.js"]))

(def ^:private ext-info
  [:separator #(str "\n;" (js-comment %) "\n")
   :banner (js-comment {:date (moment-format)})
   :trace #(println "concating" % "...")])

(defn- base []
  (apply concatenating
         jsbase-src jsbase-dest ext-info))

(defn- layout-lib []
  (apply concatenating
         jslayout-lib-src jslayout-lib-dest ext-info))

(defn- lib []
  (apply concatenating
         jslib-src jslib-dest ext-info))

(defn- all []
  (base)
  (layout-lib)
  (lib))

(defn- task [f desc]
  (f)
  (println "task" desc "finished."))

(defn -main [& [t]]
  (println (str "concat task:" t))
  (case t
    "base" (task base t)
    "layout-lib" (task layout-lib t)
    "lib" (task lib t)
    "all" (task all t)
    (println "base, layout-lib, lib, all ?")))
 