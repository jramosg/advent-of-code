(ns advent_of_code.year-2022.day-02.main-test
  (:require [clojure.test :refer :all]
            [advent_of_code.year-2022.day-02.main :as year-2022.day-02]))

(deftest get-result-test
  (is (= :draw (year-2022.day-02/get-result :rock :rock)))
  (is (every? #{:win} [(year-2022.day-02/get-result :rock :paper)
                       (year-2022.day-02/get-result :paper :scissors)
                       (year-2022.day-02/get-result :scissors :rock)]))
  (is (every? #{:lost} [(year-2022.day-02/get-result :paper :rock)
                       (year-2022.day-02/get-result :scissors :paper)
                       (year-2022.day-02/get-result :rock :scissors)])))

(deftest get-final-score-test
  (is (= 15 (year-2022.day-02/get-final-score
              ["A Y"
               "B X"
               "C Z"])))
  (is (= 11767 (year-2022.day-02/get-final-score))))

(deftest score-startegy-2-test
  (is (= 12 (year-2022.day-02/score-startegy-2
              ["A Y"
               "B X"
               "C Z"]))))
