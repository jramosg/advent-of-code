(ns advent-of-code.year-2022.day-14.main-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.year-2022.day-14.main :as year-2022.day-14]))

(def ^:const ^:private example-data
  ["498,4 -> 498,6 -> 496,6"
   "503,4 -> 502,4 -> 502,9 -> 494,9"])

(deftest part-1-test
  (is (= 24 (year-2022.day-14/part-1 example-data)))
  (is (= 692 (year-2022.day-14/part-1))))

(deftest part-2-test
  (is (= 93 (year-2022.day-14/part-2 example-data)))
  (is (= 31706 (year-2022.day-14/part-2))))
