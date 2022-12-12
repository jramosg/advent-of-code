(ns advent-of-code.year-2022.day-05.main
  (:require [advent-of-code.util :as util]
            [clojure.string :as str]))

(def ^:const ^:private space
  \space)

(defn- get-c-not-space [index s]
  (let [c (get s index)]
    (when-not (= space c)
      c)))

(defn- get-data-of-label [initial-data-str e]
  (let [index (str/index-of (last initial-data-str) e)]
    (->> initial-data-str
         vec
         pop
         (keep #(get-c-not-space index %)))))

(defn- read-input [data]
  (let [[initial-data-str _ moves] (partition-by empty? data)
        last-init-data-row (last initial-data-str)]
    {:initial-state (->>
                      last-init-data-row
                      (remove #(= space %))
                      (reduce
                        (fn [m e]
                          (assoc m
                            (str e)
                            (vec (get-data-of-label initial-data-str e))))
                        {}))
     :moves moves}))

(defn- get-moved-items [n state quantity from]
  (cond-> (take quantity (state from))
          (= n 1)
          reverse))

(defn- process-move [n state s]
  (let [[_ quantity _ from _ to] (str/split s #" ")
        quantity (util/str->int quantity)
        moved-items (get-moved-items n state quantity from)]
    (-> state
        (update to #(concat moved-items %))
        (update from #(drop quantity %)))))

(defn- process [n {:keys [initial-state moves]}]
  (reduce
    #(process-move n %1 %2)
    initial-state moves))

(defn- score [m]
  (->> m
       sort
       (map (fn [[_ coll]] (first coll)))
       (str/join "")))

(defn part-* [n & [data]]
  (->> (read-input (or data (util/slurp+split-line "05")))
       (process n)
       score))

(defn part-1 [& [data]]
  (part-* 1 data))

(defn part-2 [& [data]]
  (part-* 2 data))