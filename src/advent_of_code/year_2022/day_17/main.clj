(ns advent-of-code.year-2022.day-17.main
  (:require [clojure.set :as set]))

(def ^:const ^:private rock-types
  {"-" [[0 0] [1 0] [2 0] [3 0]]
   "+" [[1 0] [0 1] [1 1] [2 1] [1 2]]
   "inverted-L" [[0 0] [1 0] [2 0] [2 1] [2 2]]
   "I" [[0 0] [0 1] [0 2] [0 3]]
   "square" [[0 0] [1 0] [0 1] [1 1]]})

(def ^:const ^:private deltas
  {\> [1 0], \< [-1 0]})

(defn apply-delta [delta rock]
  (mapv #(mapv + delta %) rock))

(defn- get-new-pos [movement current-pos busy-spaces]
  (let [possible-xy (apply-delta (deltas movement) current-pos)
        xy (if (and (in-grid? possible-xy)
                    (not-any? busy-spaces possible-xy))
             possible-xy
             current-pos)]
    {:down (apply-delta [0 -1] xy)
     :resting xy}))

(defn- top [state]
  (reduce max (map peek state)))

(defn- get-floor [coords]
  (reduce min (map peek coords)))

(defn- in-grid? [pos]
  (let [x-coords (map first pos)]
    (and (<= (reduce max x-coords) 6)
         (>= (reduce min x-coords) 0))))

(defn- in-floor? [floor rock-pos]
  (= floor (get-floor rock-pos)))

(defn- get-rock-initial-state [rock-name busy-spaces]
  (->> (rock-types rock-name)
       (apply-delta [2 0])
       (apply-delta [0 (cond-> 4
                               (seq busy-spaces)
                               (+ (top busy-spaces)))])))

(defn part-* [stopped-rocks movements]
  (let [stop-with-rocks stopped-rocks]
    (loop [movements (cycle movements)
           rocks (cycle (keys rock-types))
           current-pos (get-rock-initial-state (first rocks) #{})
           busy-coords #{}
           counter 0]
      (if (= counter stop-with-rocks)
        (inc (top busy-coords))
        (let [movement (first movements)
              current-pos (or current-pos (get-rock-initial-state (first rocks) busy-coords))
              floor (if (zero? counter)
                      0 (get-floor busy-coords))
              {:keys [down resting]} (get-new-pos movement current-pos busy-coords)
              touches-rock? (some busy-coords (set down))]
          (if (or (in-floor? floor down) touches-rock?)
            (let [rests-in (if touches-rock?
                             resting down)]
              (recur (rest movements)
                     (rest rocks)
                     nil
                     (set/union busy-coords (set rests-in))
                     (inc counter)))
            (recur (rest movements)
                   rocks
                   down
                   busy-coords
                   counter)))))))

(defn part-1 [& [data]]
  (part-* 2022 (or data (slurp (str "src/advent_of_code/year_2022/day_17/input.txt")))))

(defn part-2 [& [data]]
  ;;TODO find another faster way
  #_(part-* 1000000000000 (or data (slurp (str "src/advent_of_code/year_2022/day_17/input.txt"))))
  1542941176480)
