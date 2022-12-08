(ns advent-of-code.year-2022.day-08.main
  (:require [advent-of-code.util :as util]))

(defn- char->int [c]
  (some-> c (Character/digit 10)))

(defn- in-edge? [data row-i col-i]
  (not-every?
    (fn [coll]
      (get-in data coll))
    [[row-i (dec col-i)]
     [row-i (inc col-i)]
     [(dec row-i) col-i]
     [(inc row-i) col-i]]))

(defn- smaller? [data cell-int row-i col-i]
  (< (char->int (get-in data [row-i col-i]))
     cell-int))

(defn- visible-row? [data cell-int row-i range-coll]
  (every?
    #(smaller? data cell-int row-i %)
    range-coll))

(defn- visible-coll? [data cell-int col-i range-row]
  (every?
    #(smaller? data cell-int % col-i)
    range-row))

(defn- visible-in-row? [data cell-int row-i col-i cols-in-row]
  (or
    (visible-row? data cell-int row-i (range (inc col-i) cols-in-row))
    (visible-row? data cell-int row-i (range col-i))))

(defn- visible-in-col? [data cell-int row-i col-i row-count]
  (or
    (visible-coll? data cell-int col-i (range (inc row-i) row-count))
    (visible-coll? data cell-int col-i (range row-i))))

(defn- visible? [c data row-i col-i cols-in-row row-count]
  (let [cell-int (char->int c)]
    (or (in-edge? data row-i col-i)
        (visible-in-row? data cell-int row-i col-i cols-in-row)
        (visible-in-col? data cell-int row-i col-i row-count))))

(defn- get-visible-trees [[row1 :as data]]
  (let [cols-count (count row1)
        rows-count (count data)]
    (keep-indexed
      (fn [row-i s]
        (keep-indexed
          (fn [col-i c]
            (when (visible? c data row-i col-i cols-count rows-count)
              true))
          s))
      data)))

(defn part-1 [& [data]]
  (-> (or data (util/slurp+split-line "08"))
      get-visible-trees
      flatten
      count))

;;;;;;;;;;;;;;;;
;;;; PART 2 ;;;;
;;;;;;;;;;;;;;;;

(defn take-until
  "Returns a lazy sequence of successive items from coll until
   (pred item) returns true, including that item. pred must be
   free of side-effects."
  [pred coll]
  (lazy-seq
    (when-let [s (seq coll)]
      (if (pred (first s))
        (cons (first s) nil)
        (cons (first s) (take-until pred (rest s)))))))

(defn- bigger+eq? [data cell-int row-i col-i]
  (>= (char->int (get-in data [row-i col-i]))
      cell-int))

(defn- score [pred coll]
  (count (take-until pred coll)))

(defn- count-visibles-in-row [data cell-int row-i range-coll]
  (score #(bigger+eq? data cell-int row-i %) range-coll))

(defn- count-visibles-in-col [data cell-int col-i range-row]
  (score #(bigger+eq? data cell-int % col-i) range-row))

(defn- num-visibles [c data row-i col-i cols-in-row row-count]
  (let [cell-int (char->int c)]
    [(count-visibles-in-row data cell-int row-i (range (inc col-i) cols-in-row))
     (count-visibles-in-row data cell-int row-i (range (dec col-i) -1 -1))
     (count-visibles-in-col data cell-int col-i (range (inc row-i) row-count))
     (count-visibles-in-col data cell-int col-i (range (dec row-i) -1 -1))]))

(defn- get-scenic-score [[row1 :as data]]
  (let [cols-count (count row1)
        rows-count (count data)]
    (map-indexed
      (fn [row-i s]
        (map-indexed
          (fn [col-i c]
            (reduce * (num-visibles c data row-i col-i cols-count rows-count)))
          s))
      data)))

(defn part-2 [& [data]]
  (->> (or data (util/slurp+split-line "08"))
       get-scenic-score
       flatten
       (reduce max)))