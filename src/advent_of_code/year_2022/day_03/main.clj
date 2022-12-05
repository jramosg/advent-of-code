(ns advent_of_code.year-2022.day-03.main
  (:require [advent-of-code.util :as util]
            [clojure.string :as str]))

(defn- uppercase? [c]
  (re-matches #"[A-Z ]+" (str c)))

(defn- char-to-puntuation [c]
  (let [char-int (int c)]
    (- char-int
       (if (uppercase? (str c))
         38
         96))))

(defn- process-single-rucksack [s]
  (let [char-count (count s)
        [chars1 chars2] (split-at (/ char-count 2) s)]
    (char-to-puntuation (some (set chars1) chars2))))

(defn priorities-sum [& [input-data]]
  (->> (or input-data (util/slurp+split-line "03"))
       (map process-single-rucksack)
       (apply +)))

(defn- get-A->z-char-array []
  (map
    char
    (range (int \A) (inc (int \z)))))

(defn- process-grouped-rucksack [A->z-char-array rucksacks]
  (some
    (fn [c]
      (when (every? #(str/includes? % (str c)) rucksacks)
        (char-to-puntuation c)))
    A->z-char-array))

(defn priorities-sum-grouped-elfs [& [input-data]]
  (let [A->z-char-array (get-A->z-char-array)]
    (->> (or input-data (util/slurp+split-line "03"))
         (partition-all 3)
         (map #(process-grouped-rucksack A->z-char-array %))
         (apply +))))
