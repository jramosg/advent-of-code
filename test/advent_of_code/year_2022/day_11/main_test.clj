(ns advent-of-code.year-2022.day-11.main-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.year-2022.day-11.main :as year-2022.day-11]
            [advent-of-code.util :as util]))

(deftest part-1-test
  (is (= 10605 (year-2022.day-11/part-1 (util/slurp+split-line "11" true))))
  (is (= 55944 (year-2022.day-11/part-1))))

(deftest part-2-test
  (is (= 2713310158 (year-2022.day-11/part-2 (util/slurp+split-line "11" true))))
  (is (= 15117269860 (year-2022.day-11/part-2))))
