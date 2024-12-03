(ns advent-of-code.year-2023.day01
  (:require
   [advent-of-code.util :as util]
   [clojure.string :as str]))

(defn part-1 []
  (reduce
   (fn [acc s]
     (let [numbers (vec (re-seq #"\d" s))]
       (-> (str (first numbers) (peek numbers))
           parse-long
           (+ acc))))
   0 (util/read-input "2023" "01")))

(defn part-2 []
  (let [digits ["one" "two" "three" "four" "five" "six" "seven" "eight" "nine"]
        pattern (re-pattern (format "(?=(%s))" (str/join "|" (conj digits "\\d"))))
        digits-translator (zipmap digits (range 1 10))
        ->digits (fn [[_ n]] (digits-translator n n))]
    (reduce
     (fn [acc s]
       (let [numbers (vec (re-seq pattern s))]
         (-> (str (->digits (first numbers))
                  (->digits (peek numbers)))
             parse-long
             (+ acc))))
     0 (util/read-input "2023" "01"))))

(comment 
  (part-1)
  ;; => 54916
  (part-2)
  ;; => 54728
  )