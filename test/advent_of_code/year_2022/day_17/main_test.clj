(ns advent-of-code.year-2022.day-17.main-test
  (:require [clojure.test :refer :all]
            [advent-of-code.year-2022.day-17.main :as year-2022.day-17]))

(def ^:const ^:private sample-data
  ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")

(deftest part-*-test
  (is (= 6 (year-2022.day-17/part-* 3 sample-data)))
  (is (= 17 (year-2022.day-17/part-* 10 sample-data))))

(deftest part-1-test
  (is (= 3068 (year-2022.day-17/part-1 sample-data)))
  (is (= 3127 (year-2022.day-17/part-1 ))))

(run-tests)
