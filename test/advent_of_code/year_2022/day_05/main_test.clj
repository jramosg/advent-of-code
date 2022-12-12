(ns advent-of-code.year-2022.day-05.main-test
  (:require
    [clojure.test :refer :all]
    [advent-of-code.year-2022.day-05.main :as year-2022.day-05]
    [advent-of-code.util :as util]))

(deftest part-1-test
  (is (= "CMZ" (year-2022.day-05/part-1 (util/slurp+split-line "05" true))))
  (is (= "WHTLRMZRC" (year-2022.day-05/part-1))))

(deftest part-2-test
  (is (= "MCD" (year-2022.day-05/part-2 (util/slurp+split-line "05" true))))
  (is (= "GMPMLWNMG" (year-2022.day-05/part-2))))
