(ns advent-of-code.year-2024.day06
  (:require
   [advent-of-code.util :as util]
   [clojure.set :as set]))

(def obstruction \#)

(def ->new-direction {[-1 0] [0 1]
                      [0 1] [1 0]
                      [1 0] [0 -1]
                      [0 -1] [-1 0]})

(defn- get-pos0 [input-coords]
  ((set/map-invert input-coords) \^))

(defn- input-by-coords []
  (reduce-kv
   (fn [acc row-index row]
     (reduce
      (fn [acc-map [col-index cell]]
        (assoc acc-map [row-index col-index] cell))
      acc
      (map-indexed vector row)))
   {} (util/read-input "2024" "06")))

(defn move-guard [pos0 input-coords]
  (loop [direction [-1 0]
         current-pos pos0
         visited {pos0 #{direction}}]
    (let [new-coords (mapv + current-pos direction)
          in-coord (input-coords new-coords)]
      (cond
        ;;
        (= in-coord obstruction)
        (recur (->new-direction direction)
               current-pos 
               visited)
        ;;
        (not in-coord) (keys visited)
        ;;
        (get-in visited [new-coords direction])
        ::looping
        ;;
        :else
        (recur direction
               new-coords
               (update visited new-coords (fnil conj #{}) direction))))))

(defn part1 []
  (let [input-coords (input-by-coords)
        pos0 (get-pos0 input-coords)]
    (count (move-guard pos0 input-coords))))

(defn part2 []
  (let [input-coords (input-by-coords)
        pos0 (get-pos0 input-coords)]
    (->> (move-guard pos0 input-coords)
         (filter
          (fn [coord]
            (and (not= coord pos0)
                 (= ::looping (move-guard pos0 (assoc input-coords coord obstruction))))))
         count)))

(comment
  (part1)
  ;; => 5162
  (time (part2))
  ;; => 1909
  )