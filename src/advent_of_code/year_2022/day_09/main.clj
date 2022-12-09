(ns advent-of-code.year-2022.day-09.main
  (:require [advent-of-code.util :as util]
            [clojure.string :as str]
            [clojure.set :as set]))

(def ^:const ^:private moves
  {"R" [1 0]
   "L" [-1 0]
   "U" [0 1]
   "D" [0 -1]})

(defn- towards [vh vt dist]
  (cond-> vt
          (> (- vh vt) dist)
          inc
          (> (- vt vh) dist)
          dec))

(defn- chase-head [[hx hy] [tx ty]]
  (cond
    (and (<= (Math/abs (- hx tx)) 1)
         (<= (Math/abs (- hy ty)) 1))
    [tx ty]

    (= hx tx)
    [hx (towards hy ty 1)]

    (= hy ty)
    [(towards hx tx 1) hy]

    :else
    [(towards hx tx 0)
     (towards hy ty 0)]))

(defn move [prev-pos f steps]
  (+ prev-pos (* f steps)))

(defn- passed-from [dir steps {[hx hy] :h :as current-position}]
  (let [[x-move y-move] (get moves dir)]
    (loop [steps (range 1 (inc (util/str->int steps)))
           position current-position
           tail-seen #{}]
      (if-let [step (first steps)]
        (let [head-pos [(move hx x-move step)
                        (move hy y-move step)]
              tail-pos (chase-head head-pos (:t position))]
          (recur
            (rest steps)
            {:h head-pos :t tail-pos}
            (conj tail-seen tail-pos)))
        {:position position
         :tail-seen tail-seen}))))

(defn part-1 [& [data]]
  (loop [moves (or data (util/slurp+split-line "09"))
         current-pos {:h [0 0] :t [0 0]}
         tail-visits #{[0 0]}]
     (if-let [[dir steps] (some-> moves first (str/split #" "))]
       (let [{:keys [position tail-seen]}
             (passed-from dir steps current-pos)]
         (recur
           (rest moves)
           position
           (set/union tail-visits tail-seen)))
       (count tail-visits))))

;;;;;;;;;;;;;;;;
;;;; PART 2 ;;;;
;;;;;;;;;;;;;;;;

(defn- passed-from2 [dir steps current-position]
  (let [[x-move y-move] (get moves dir)]
    (loop [steps (range 1 (inc (util/str->int steps)))
           {:keys [current-position tail-seen]}
           {:current-position current-position
            :tail-seen #{}}]
      (if (seq steps)
        (recur (rest steps)
               (loop [i 0
                      current-position current-position
                      tail-positions tail-seen]
                 (cond
                   (not (get current-position i))
                   {:current-position current-position
                    :tail-seen tail-positions}
                   (not (get current-position (dec i)))
                   (recur (inc i)
                          (update current-position i
                                  (fn [[hx hy]]
                                    [(move hx x-move 1)
                                     (move hy y-move 1)]))
                          tail-positions)
                   :else
                   (let [head-pos (get current-position (dec i))
                         tail-pos (chase-head head-pos (get current-position i))]
                     (recur (inc i)
                            (assoc current-position
                              i tail-pos)
                            (if (= i 9)
                              (conj tail-positions tail-pos)
                              tail-positions))))))
        {:current-position current-position
         :tail-seen tail-seen}))))

(defn part-2 [& [data]]
  (loop [moves (or data (util/slurp+split-line "09"))
         current-pos (reduce
                       (fn [m i]
                         (assoc m i [0 0]))
                      {} (range 10))
         tail-visits #{[0 0]}]
    (if-let [[dir steps] (some-> moves first (str/split #" "))]
      (let [{:keys [current-position tail-seen]}
            (passed-from2 dir steps current-pos)]
        (recur
          (rest moves)
          current-position
          (set/union tail-visits tail-seen)))
      (count tail-visits))))