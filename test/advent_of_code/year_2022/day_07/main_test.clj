(ns advent-of-code.year-2022.day-07.main-test
  (:require [clojure.string :as str]
            [clojure.test :refer :all]
            [advent-of-code.year-2022.day-07.main :as year-2022.day-07]))

(deftest part-1-test
  (is (= 95437 (year-2022.day-07/part-1 (->> (str "src/advent_of_code/year_2022/day_07/example-input.txt")
                                             slurp
                                             str/split-lines))))
  (is (= 1501149 (year-2022.day-07/part-1))))

(deftest part-2-test
  (is (= 24933642 (year-2022.day-07/part-2 (->> (str "src/advent_of_code/year_2022/day_07/example-input.txt")
                                                slurp
                                                str/split-lines))))
  (is (= 10096985 (year-2022.day-07/part-2))))

(run-tests)
