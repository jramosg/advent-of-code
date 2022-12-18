(ns advent-of-code.year-2022.day-18.main-test
  (:require
    [advent-of-code.util :as util]
    [clojure.test :refer :all]
    [advent-of-code.year-2022.day-18.main :as year-2022.day-18]))

(deftest part-1-test
  (is (= 64 (year-2022.day-18/part-1 (util/slurp+split-line "18" true))))
  (is (= 4500 (year-2022.day-18/part-1))))

(deftest part-2-test
  (is (= 58 (year-2022.day-18/part-2 (util/slurp+split-line "18" true))))
  (is (= 2558 (year-2022.day-18/part-2))))
