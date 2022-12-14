(ns advent-of-code.util
  (:require [clojure.string :as str]))

(defn slurp+split-line [day-str & [test?]]
  (->> (if test?
         "/example-input.txt"
         "/input.txt")
       (str "src/advent_of_code/year_2022/day_" day-str )
       slurp
       str/split-lines))

(defn str->int [s]
  (Integer/parseInt s))