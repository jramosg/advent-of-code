(ns advent-of-code.year-2022.day-09.main
  (:require [advent-of-code.util :as util]
            [clojure.string :as str]
            [clojure.set :as set]))

(def ^:const ^:private deltas
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

(defn- loop-tails [{:keys [current-position tail-seen]} dir knots]
  (let [[x-move y-move] (get deltas dir)]
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
                 (if (= i (dec knots))
                   (conj tail-positions tail-pos)
                   tail-positions)))))))

(defn- passed-from2 [dir steps current-position knots]
  (loop [steps (range 1 (inc (util/str->int steps)))
         {:keys [current-position tail-seen] :as status}
         {:current-position current-position
          :tail-seen #{}}]
    (if (seq steps)
      (recur
        (rest steps)
        (loop-tails status dir knots))
      {:current-position current-position
       :tail-seen tail-seen})))

(defn part-* [knots data]
  (loop [moves data
         current-pos (reduce
                       (fn [m i]
                         (assoc m i [0 0]))
                      {} (range knots))
         tail-visits #{[0 0]}]
    (if-let [[dir steps] (some-> moves first (str/split #" "))]
      (let [{:keys [current-position tail-seen]}
            (passed-from2 dir steps current-pos knots)]
        (recur
          (rest moves)
          current-position
          (set/union tail-visits tail-seen)))
      (count tail-visits))))

(defn part-1 [& [data]]
  (part-* 2 (or data (util/slurp+split-line "09"))))

(defn part-2 [& [data]]
  (part-* 10 (or data (util/slurp+split-line "09"))))