(ns advent-of-code.year-2022.day-09.main-test
  (:require [clojure.test :refer :all]
            [advent-of-code.year-2022.day-09.main :as year-2022.day-09]
            [clojure.string :as str]))

(def ^:private ^:const example-data
  (->> (str "src/advent_of_code/year_2022/day_09/example-input.txt")
       slurp
       str/split-lines))

(deftest part-1-test
  (is (= 13 (year-2022.day-09/part-1 example-data)))
  (is (= 6209 (year-2022.day-09/part-1))))

(deftest part-2-test
  (is (= 1 (year-2022.day-09/part-2 example-data)))
  (is (= 36 (year-2022.day-09/part-2 ["R 5"
                                      "U 8"
                                      "L 8"
                                      "D 3"
                                      "R 17"
                                      "D 10"
                                      "L 25"
                                      "U 20"])))
  (is (= 2460 (year-2022.day-09/part-2))))
