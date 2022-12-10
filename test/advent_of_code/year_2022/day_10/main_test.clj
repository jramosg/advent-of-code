(ns advent-of-code.year-2022.day-10.main-test
  (:require [clojure.test :refer :all]
            [advent-of-code.year-2022.day-10.main :as year-2022.day-10]
            [advent-of-code.util :as util]))

(deftest part-1-test
  (is (= 13140 (year-2022.day-10/part-1 (util/slurp+split-line 10 true))))
  (is (= 12640 (year-2022.day-10/part-1))))

(deftest part-2-test
  (is (= ["####.#..#.###..####.#....###....##.###.#"
          "#....#..#.#..#....#.#....#..#....#.#..##"
          "###..####.###....#..#....#..#....#.#..##"
          "#....#..#.#..#..#...#....###.....#.###.."
          "#....#..#.#..#.#....#....#.#..#..#.#.#.#"
          "####.#..#.###..####.####.#..#..##..#..#."]
         (year-2022.day-10/part-2))) )
