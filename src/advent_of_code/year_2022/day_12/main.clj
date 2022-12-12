(ns advent-of-code.year-2022.day-12.main
  (:require [advent-of-code.util :as util]
            [clojure.string :as str]))

(defn- coord-finder [data c row]
  (when-let [col-index (str/index-of row c)]
    [(.indexOf data row) col-index]))

(defn- find-single-coord [data c]
  (some #(coord-finder data c %) data))

(defn- find-coords [data c]
  (keep #(coord-finder data c %) data))

(def ^:const ^:private deltas
  {"R" [1 0]
   "L" [-1 0]
   "U" [0 1]
   "D" [0 -1]})

(def ^:const ^:private start-end-chars-map
  {\S \a
   \E \z})

(defn- vec-pos->int [data pos]
  (let [c (get-in data pos)]
    (int (get start-end-chars-map c c))))

(defn- one-step? [data current-pos new-pos]
  (<= (dec (vec-pos->int data new-pos))
      (vec-pos->int data current-pos)))

(defn- in-grid? [[new-posy new-posx] max-x max-y]
  (and (<= new-posx max-x)
       (<= new-posy max-y)))

(defn- can-change-pos? [data current-pos new-pos max-x max-y]
  (and (not-any? neg? new-pos)
       (in-grid? new-pos max-x max-y)
       (one-step? data current-pos new-pos)))

(defn- move
  ([data current-pos max-x max-y]
   (keep
     #(move data current-pos max-x max-y %)
     (vals deltas)))
  ([data current-pos max-x max-y delta]
   (let [new-pos (mapv + delta current-pos)]
     (when (can-change-pos? data current-pos new-pos max-x max-y)
       new-pos))))

(defn- get-neighbors [data visit max-x max-y seen to-visit]
  (keep
    (fn [[_ delta]]
      (let [neighbor (move data visit max-x max-y delta)]
        (when (and neighbor
                   (not (seen neighbor))
                   (not-any? #(= % neighbor) to-visit))
          neighbor)))
    deltas))

(defn find-path [[row0 :as data] starts end]
  (let [max-x (dec (count row0))
        max-y (dec (count data))]
    (loop [to-visit (into (clojure.lang.PersistentQueue/EMPTY) starts)
           seen (zipmap starts (repeat 0))]
      (let [visit (peek to-visit)]
        (if (= end visit)
          (seen visit)
          (let [neighbors (vec (get-neighbors data visit max-x max-y seen to-visit))]
            (recur (into (pop to-visit) neighbors)
                   (merge seen
                          (zipmap neighbors (repeat (inc (seen visit))))))))))))


(defn part-1 [& [data]]
  (let [data (or data (util/slurp+split-line "12"))]
    (find-path data [(find-single-coord data \S)] (find-single-coord data \E))))

(defn part-2 [& [data]]
  (let [data (or data (util/slurp+split-line "12"))]
    (find-path data
               (into [(find-single-coord data \S)]
                     (find-coords data \a))
               (find-single-coord data \E))))