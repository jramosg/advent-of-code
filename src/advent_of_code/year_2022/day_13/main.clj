(ns advent-of-code.year-2022.day-13.main
  (:require [advent-of-code.util :as util]))

(defn- num->vec [e]
  (if (number? e)
    [e]
    e))

(defn- compare-colls [coll1 coll2]
  (let [coll1-count (count coll1)]
    (->>
      (if (zero? coll1-count)
        1 coll1-count)
      range
      (map
        (fn [i]
          (let [e1 (get coll1 i)
                e2 (get coll2 i)]
            (cond
              (and (nil? e1) e2)
              true
              (nil? e2)
              false
              (every? number? [e1 e2])
              (cond (< e1 e2)
                    true
                    (= e1 e2)
                    "same")
              :else
              (compare-colls (num->vec e1)
                             (num->vec e2)))))))))

(defn process-coll-pair [[s1 :as coll]]
  (when (seq s1)
    (let [[coll1 coll2] (map read-string coll)
          result (flatten (compare-colls coll1 coll2))]
      (or (some true? result)
          (every? #{"same"} result)))))

(defn process [input]
  (->> input
       (partition-by empty?)
       (keep process-coll-pair)))

(defn- score [result]
  (reduce
    +
    (keep-indexed
      (fn [i e]
        (when e (inc i)))
      result)))

(defn part-1 [& [data]]
  (->> (or data (util/slurp+split-line "13"))
       process
       score))