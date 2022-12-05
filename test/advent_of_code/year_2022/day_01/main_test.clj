(ns advent_of_code.year-2022.day-01.main-test
  (:require [clojure.test :refer :all]
            [advent_of_code.year-2022.day-01.main :as year-2022.day-01.main]))

(deftest max-calories-test
  (is (= 68787 (year-2022.day-01.main/max-calories-from-one-elf))))

(deftest max-calories-in-three-elfs-test
  (is (= 198041 (year-2022.day-01.main/max-calories-in-three-elfs))))
