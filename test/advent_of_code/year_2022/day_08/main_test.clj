(ns advent-of-code.year-2022.day-08.main-test
  (:require [clojure.test :refer :all]
            [advent-of-code.year-2022.day-08.main :as year-2022.day-08]))

(deftest part-1-test
  (is (= 21 (year-2022.day-08/part-1 ["30373"
                                      "25512"
                                      "65332"
                                      "33549"
                                      "35390"])))
  (is (= 1703 (year-2022.day-08/part-1))))

(deftest part-2-test
  (is (= 8 (year-2022.day-08/part-2 ["30373"
                                      "25512"
                                      "65332"
                                      "33549"
                                      "35390"])))
  (is (= 496650 (year-2022.day-08/part-2))))

(run-tests)