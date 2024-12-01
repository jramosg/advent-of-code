(ns advent-of-code.year-2024.day02
  (:require
   [advent-of-code.util :as util]))

(defn- line-safe? [coll]
  (and (or (apply < coll)
           (apply > coll))
       (loop [[n1 n2 :as remaining] coll]
         (if (and n1 n2)
           (if (<= (abs (- n1 n2)) 3)
             (recur (rest remaining))
             false)
           true))))

(defn part-1 []
  (count
   (filter
    #(line-safe? (map parse-long (re-seq #"\d+" %)))
    (util/read-input "2024" "02"))))

(defn- some-level-safe [line]
  (some
   (fn [idx] (->> line 
                  (drop (inc idx))
                  (concat (take idx line))
                  line-safe?))
   (range 0 (count line))))

(defn part-2 []
  (count
   (filter
    (fn [s]
      (let [line (map parse-long (re-seq #"\d+" s))]
        (or (line-safe? line)
            (some-level-safe  line))))
    (util/read-input "2024" "02"))))

(comment
  (part-1)
  ;; => 598
  (part-2)
  ;; => 634
  )