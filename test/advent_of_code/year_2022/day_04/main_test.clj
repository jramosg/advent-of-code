(ns advent-of-code.year-2022.day-04.main-test
  (:require [clojure.test :refer :all]
            [advent-of-code.year-2022.day-04.main :as year-2022.day-04]))

(def ^:const ^:private example-data
  ["2-4,6-8"
   "2-3,4-5"
   "5-7,7-9"
   "2-8,3-7"
   "6-6,4-6"
   "2-6,4-8"])

(deftest count-repeated-assignment-pairs-test
  (is (= 2 (year-2022.day-04/count-repeated-assignment-pairs
             example-data)))
  (is (= 602 (year-2022.day-04/count-repeated-assignment-pairs))))

(deftest count-overlaping-assigment-pairs-test
  (is (= 4 (year-2022.day-04/count-overlaping-assigment-pairs
             example-data)))
  (is (= 891 (year-2022.day-04/count-overlaping-assigment-pairs))))
