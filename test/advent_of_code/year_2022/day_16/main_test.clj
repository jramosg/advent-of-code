(ns advent-of-code.year-2022.day-16.main-test
  (:require
    [advent-of-code.util :as util]
    [clojure.test :refer :all]
    [advent-of-code.year-2022.day-16.main :as year-2022.day-16]))

(deftest part-1-test
  (is (= 1651 (year-2022.day-16/part-1 (util/slurp+split-line "16" true))))
  (is (= 2181 (year-2022.day-16/part-1))))
