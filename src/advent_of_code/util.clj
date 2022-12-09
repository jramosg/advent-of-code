(ns advent-of-code.util
  (:require [clojure.string :as str]))

(defn slurp+split-line [day-str]
  (->> (str "src/advent_of_code/year_2022/day_" day-str "/input.txt")
       slurp
       str/split-lines))

(defn str->int [s]
  (Integer/parseInt s))