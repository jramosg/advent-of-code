(ns advent-of-code.year-2024.day04
  (:require
   [advent-of-code.util :as util]
   [clojure.string :as str]))

(defn input-grid []
  (reduce-kv
   (fn [acc row-index col]
     (reduce
      (fn [acc-map [col-index c]]
        (assoc acc-map [row-index col-index] c))
      acc (map-indexed vector col)))
   {} (util/read-input "2024" "04")))

(defn part1 []
  (let [directions (for [x [-1 0 1]
                         y [-1 0 1]
                         :when (not-every? zero? [x y])]
                     (map #(map (partial * %) [x y]) (range 1 4)))
        grid (input-grid)
        xmas "XMAS"]
    (reduce-kv
     (fn [total-count position ch]
       (cond-> total-count
         (= ch \X)
         (and
          (+ (count
              (filter
               (fn [direction]
                 (= (reduce
                     (fn [current-string step]
                       (if-let [next-ch (grid (mapv + position step))]
                         (let [new-string (str current-string next-ch)]
                           (if (str/starts-with? xmas new-string)
                             new-string
                             (reduced false)))
                         (reduced false)))
                     "X" direction)
                    xmas))
               directions))))))
     0 grid)))

(defn part2 []
  (let [directions [[-1 -1] [-1 1] [1 1] [1 -1]]
        grid (input-grid)
        x-mas-patterns #{"SAM" "MAS"}
        x-mas-chars #{\S \A \M}]
    (reduce-kv
     (fn [total-count position ch]
       (cond-> total-count
         (and (= ch \A)
              (= 2
                 (when-let [m (reduce
                               (fn [acc step]
                                 (if-let [next-ch (grid (mapv + position step))]
                                   (if (x-mas-chars next-ch)
                                     (assoc acc step next-ch)
                                     (reduced nil))
                                   (reduced nil)))
                               {[0 0] \A} directions)]
                   (count (filter x-mas-patterns
                                  [(str (m [-1 -1]) (m [0 0]) (m [1 1]))
                                   (str (m [-1 1]) (m [0 0]) (m [1 -1]))])))))
         inc))
     0 grid)))

(comment
  (time (part1))
  ; "Elapsed time: 100.185033 msecs"
  ;; => 2370
  (time (part2))
  ; "Elapsed time: 65.81693 msecs"
  ;; => 1908
  )