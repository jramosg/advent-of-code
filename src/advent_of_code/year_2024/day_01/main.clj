(ns advent-of-code.year-2024.day-01.main
  (:require
   [advent-of-code.util :as util]
   [clojure.string :as str]))

(defn- read-input []
  (map
   (fn [line]
     (map parse-double (str/split line #"\W+")))
   (util/read-input "2024" "01")))

(defn part-1 []
  (let [input (read-input)
        sorted-left (sort-by first input)
        sorted-right (sort-by second input)
        distance #(Math/abs (- (first %1) (second %2)))]
    (apply + (map distance sorted-left sorted-right))))

(defn part-2 []
  (let [input (read-input)
        freqs-right-side (frequencies (map second input))]
    (int
     (reduce
      (fn [acc [n]]
        (+ acc (* n (freqs-right-side n 0))))
      0 input))))


(comment
  (part-1)
  ;; => 2066446.0 

  (part-2)
  ;; => 24931009
  )