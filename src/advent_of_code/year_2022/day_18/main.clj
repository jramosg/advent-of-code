(ns advent-of-code.year-2022.day-18.main
  (:require [advent-of-code.util :as util]
            [clojure.string :as str]))

(def ^:const ^:provate deltas
  [[1 0 0] [-1 0 0]
   [0 1 0] [0 -1 0]
   [0 0 1] [0 0 -1]])

(defn- apply-delta [coord delta]
  (mapv + coord delta))

(defn- row->coords [row]
  (mapv
    util/str->int
    (str/split row #",")))

(defn- parse-input [input]
  (set (map row->coords input)))

(defn- apply-deltas [input block]
  (keep
    #(let [p (apply-delta block %)]
       (when-not (input p) p))
    deltas))

(defn- surface [input]
  (mapcat
    #(apply-deltas input %)
    input))

(defn part-1 [& [data]]
  (let [data (or data (util/slurp+split-line "18"))]
    (-> (parse-input data)
        surface
        count)))

(defn unseen [input]
  (let [bound (apply max (flatten (seq input)))]
    (loop [frontier #{[0 0 0]}
           unseen (set (for [x (range 0 (inc bound))
                             y (range 0 (inc bound))
                             z (range 0 (inc bound))
                             :let [p [x y z]]
                             :when (not (input p))]
                         p))]
      (if (empty? frontier)
        unseen
        (let [visit (first frontier)
              to-visit (keep
                         #(let [dest (apply-delta visit %)]
                            (when (and (unseen dest)
                                       (not (frontier dest)))
                              dest))
                         deltas)]
          (recur (-> frontier
                     (disj visit)
                     (into to-visit))
                 (disj unseen visit)))))))

(defn part-2 [& [data]]
  (let [data (or data (util/slurp+split-line "18"))
        input (parse-input data)]
    (-> input
        unseen
        (into input)
        surface
        count)))