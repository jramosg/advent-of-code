(ns advent-of-code.year-2022.day-12.main-test
  (:require [clojure.test :refer :all]
            [advent-of-code.year-2022.day-12.main :as year-2022.day-12]))

(def ^:private ^:const example-input
  ["Sabqponm"
   "abcryxxl"
   "accszExk"
   "acctuvwj"
   "abdefghi"])

(deftest part-1-test
  (is (= 31 (year-2022.day-12/part-1 example-input)))
  (is (= 361 (year-2022.day-12/part-1))))

(deftest part-2-test
  (is (= 29 (year-2022.day-12/part-2 example-input)))
  (is (= 354 (year-2022.day-12/part-2))))
