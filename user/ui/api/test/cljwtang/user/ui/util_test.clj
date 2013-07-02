(ns yuntang.user.ui.util-test
  (:use clojure.test
        yuntang.user.ui.util))

(deftest check-username-pattern-test
  (is (nil? (check-username-pattern "itang")))
  (is (nil? (check-username-pattern "itang@test.com")))
  (is (nil? (check-username-pattern "itang1")))
  (is (nil? (check-username-pattern "1itang")))
  (is (nil? (check-username-pattern ".itang")))
  (is (nil? (check-username-pattern "itang-com")))
  (is (nil? (check-username-pattern "itang-ok_1")))
  
  (is (not (nil? (check-username-pattern "itang 1"))))
  (is (not (nil? (check-username-pattern "中文itang"))))
  (is (not (nil? (check-username-pattern "?itang")))))
