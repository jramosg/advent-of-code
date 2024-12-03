(ns advent-of-code.year-2023.day02
  (:require
   [advent-of-code.util :as util]
   [clojure.string :as str]))

(defn part-1 []
  (let [maxs {"red" 12
              "green" 13
              "blue" 14}]
    (reduce
     (fn [acc s]
       (let [[_ id details] (re-matches #"Game (\d+): (.*)" s)] 
         (if (some
              (fn [s]
                (some
                 (fn [[_ quantity color]]
                   (> (parse-long quantity)
                      (maxs color)))
                 (re-seq #"(\d+)\s*(red|green|blue)" s)))
              (str/split details #"; "))
           acc
           (+ acc (parse-long id)))))
     0 (util/read-input "2023" "02"))))

(comment
  (part-1)
  ;;=> 2317
  )