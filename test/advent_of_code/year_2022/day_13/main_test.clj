(ns advent-of-code.year-2022.day-13.main-test
  (:require [clojure.test :refer :all]
            [advent-of-code.year-2022.day-13.main :as year-2022.day-13]
            [advent-of-code.util :as util]))

(deftest part-1-test
  (is (= 13 (year-2022.day-13/part-1 (util/slurp+split-line "13" true))))
  (is (= 5605 (year-2022.day-13/part-1))))

(deftest part-2-test
  (is (= 140 (year-2022.day-13/part-2 (util/slurp+split-line "13" true))))
  (is (= 24969 (year-2022.day-13/part-2))))
