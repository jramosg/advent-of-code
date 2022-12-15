(ns advent-of-code.year-2022.day-14.main
  (:require [advent-of-code.util :as util]
            [clojure.set :as set]
            [clojure.string :as str]))

(def ^:const ^:private start-coord [500 0])
(def ^:const ^:private deltas [[0 1] [-1 1] [1 1]])

(defn towards [vh vt]
  (let [dist (- vt vh)]
    (cond-> vt
            (neg? dist)
            inc
            (pos? dist)
            dec)))

(defn- take-moves [dist coords]
  (take (inc (Math/abs dist)) coords))

(defn build-path [[hx hy] [tx ty]]
  (cond
    (= hx tx)
    (take-moves
      (- hy ty)
      (iterate (fn [[_ ht]] [hx (towards ty ht)]) [hx hy]))
    (= hy ty)
    (take-moves
      (- hx tx)
      (iterate (fn [[ht]] [(towards tx ht) hy]) [hx hy]))))

(defn- coord-str->vec [row]
  (let [coords-str (str/split (str/replace row " -> " " ") #" ")]
    (reduce
      (fn [coll coord-str]
        (let [to (mapv util/str->int (str/split coord-str #","))
              from (peek coll)]
          (if (empty? from)
            (conj coll to)
            (apply conj coll (rest (build-path from to))))))
      [] coords-str)))

(defn- rock-blocked-coords [data]
  (reduce
    (fn [s row]
      (set/union s (set (coord-str->vec row))))
    #{} data))

(defn- apply-delta [pos delta]
  (mapv + pos delta))

(defn- in-grid? [[new-posx new-posy] {:keys [min-x max-x min-y max-y]}]
  (and (<= min-x new-posx max-x)
       (<= min-y new-posy max-y)))

(defn- get-next-position [position applies-fn]
  (some
    (fn [delta]
      (let [new-pos (apply-delta position delta)]
        (when (applies-fn new-pos)
          new-pos)))
    deltas))

(defn- get-resting-point
  [{:keys [rocks sand] :as state} position grids]
  (when (in-grid? position grids)
    (let [new-pos (get-next-position position
                                     #(or (rocks %) (sand %)))]
      (if new-pos
        (get-resting-point state new-pos grids)
        position))))

(defn drop-sand [blocked-coords grids]
  (loop [state {:rocks blocked-coords
                :sand #{}}]
    (if-let [resting-point (get-resting-point state start-coord grids)]
      (recur (update state :sand conj resting-point))
      (count (:sand state)))))

(defn- find-grids [coords]
  (let [xs (map first coords)
        ys (map peek coords)]
    {:min-x (reduce min xs)
     :max-x (reduce max xs)
     :min-y 0
     :max-y (reduce max ys)}))

(defn part-1 [& [data]]
  (let [data (or data (util/slurp+split-line "14"))
        rock-positions (rock-blocked-coords data)
        grids (find-grids rock-positions)]
    (drop-sand rock-positions grids)))

;;;;;;;;;;;;;;;;
;;;; PART 2 ;;;;
;;;;;;;;;;;;;;;;

(defn- get-resting-point2
  [{:keys [rocks sand] :as state} position floor]
  (let [new-pos (get-next-position
                  position
                  (fn [[_ y :as xy]]
                    (and (not (rocks xy))
                         (not (sand xy))
                         (< y floor))))]
    (if new-pos
      (get-resting-point2 state new-pos floor)
      position)))

(defn drop-sand2 [blocked-coords floor]
  (loop [state {:rocks blocked-coords
                :sand #{}}]
    (let [resting-point (get-resting-point2 state start-coord floor)]
      (if (= start-coord resting-point)
        (-> state
            :sand
            count
            inc)
        (recur (update state :sand conj resting-point))))))

(defn part-2 [& [data]]
  (let [data (or data (util/slurp+split-line "14"))
        rock-positions (rock-blocked-coords data)]
    (drop-sand2 rock-positions (-> (find-grids rock-positions)
                                   :max-y
                                   (+ 2)))))