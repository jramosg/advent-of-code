(ns advent-of-code.year-2022.day-06.main-test
  (:require [clojure.test :refer :all])
  (:require [advent-of-code.year-2022.day-06.main :as year-2022.day-06]))

(deftest part-1-test
  (is (= 5 (year-2022.day-06/part-1 "bvwbjplbgvbhsrlpgdmjqwftvncz")))
  (is (= 6 (year-2022.day-06/part-1 "nppdvjthqldpwncqszvftbrmjlhg")))
  (is (= 10 (year-2022.day-06/part-1 "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")))
  (is (= 11 (year-2022.day-06/part-1 "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")))
  (is (= 1651 (year-2022.day-06/part-1))))

(deftest part-2-test
  (is (= 19 (year-2022.day-06/part-2 "mjqjpqmgbljsphdztnvjfqwrcgsmlb")))
  (is (= 23 (year-2022.day-06/part-2 "bvwbjplbgvbhsrlpgdmjqwftvncz")))
  (is (= 23 (year-2022.day-06/part-2 "nppdvjthqldpwncqszvftbrmjlhg")))
  (is (= 29 (year-2022.day-06/part-2 "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")))
  (is (= 26 (year-2022.day-06/part-2 "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")))
  (is (= 3837 (year-2022.day-06/part-2))))
