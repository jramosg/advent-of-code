(ns advent_of_code.year-2022.day-01.main
  (:require [advent-of-code.util :as util]))

(defn- data-by-elf []
  (partition-by #{""} (util/slurp+split-line "01")))

(defn- get-elfs-calories [elf-data]
  (reduce
    (fn [n s]
      (if (seq s)
        (+ n (Integer/parseInt s))
        n))
    0 elf-data))

(defn max-calories-from-one-elf []
  (->> (data-by-elf)
       (map get-elfs-calories)
       (apply max)))

(defn max-calories-in-three-elfs []
  (->> (data-by-elf)
       (map get-elfs-calories)
       (sort #(compare %2 %1))
       (take 3)
       (reduce +)))

