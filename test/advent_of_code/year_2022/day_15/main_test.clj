(ns advent-of-code.year-2022.day-15.main-test
  (:require
    [clojure.test :refer :all]
    [advent-of-code.util :as util]
    [advent-of-code.year-2022.day-15.main :as year-2022.day-15]))

(deftest part-1-test
  (is (= 26 (year-2022.day-15/part-1 10 (util/slurp+split-line "15" true))))
  (is (= 4748135 (year-2022.day-15/part-1 2000000))))

(deftest part-2-test
  (is (= 56000011 (year-2022.day-15/part-2 (util/slurp+split-line "15" true))))
  (is (= 13743542639657 (year-2022.day-15/part-2 (util/slurp+split-line "15" true)))))
