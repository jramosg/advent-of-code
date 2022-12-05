(ns advent_of_code.year-2022.day-03.main-test
  (:require [clojure.test :refer :all]
            [advent_of_code.year-2022.day-03.main :as year-2022.day-03]))

(def ^:const ^:private example-data
  ["vJrwpWtwJgWrhcsFMMfFFhFp"
   "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"
   "PmmdzqPrVvPwwTWBwg"
   "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn"
   "ttgJtRGJQctTZtZT"
   "CrZsJsPPZsGzwwsLwLmpwMDw"])

(deftest priorities-sum-test
  (is (= 157 (year-2022.day-03/priorities-sum example-data))))

(deftest priorities-sum-grouped-elfs-test
  (is (= 70 (year-2022.day-03/priorities-sum-grouped-elfs example-data))))

