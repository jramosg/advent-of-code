(ns advent-of-code.year-2024.day03
  ;;https://adventofcode.com/2024/day/3
  (:require
   [advent-of-code.util :as util]))

(defn mul-str [s]
  (->> (re-seq #"\d+" s)
       (map parse-long)
       (reduce *)))

(defn part-01 []
  (->> (util/read-input "2024" "03")
       (apply str)
       (re-seq #"mul\(\d+,\d+\)")
       (map mul-str)
       (reduce +)))

(defn part-02 []
  (loop [[i1 i2 :as coll] (->> (util/read-input "2024" "03")
                               (apply str)
                               (re-seq #"(?:don't|do|mul\(\d+,\d+\))")
                               (partition-by #{"don't" "do"}))
         acc 0]
    (cond
      (empty? coll) acc
      ;;
      (not (#{"don't" "do"} (first i1)))
      (recur (next coll)
             (+ acc (reduce + (map mul-str i1))))
      ;;
      (= (first i1) "don't")
      (recur (drop 2 coll)
             acc)
      ;;
      :else (recur (drop 2 coll) 
                   (+ acc (reduce + (map mul-str i2)))))))

(comment
  (part-01)
   ;; => 181345830
  (part-02)
  ;; => 98729041
 )