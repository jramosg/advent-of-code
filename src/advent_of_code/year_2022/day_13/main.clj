(ns advent-of-code.year-2022.day-13.main
  (:require [advent-of-code.util :as util]))

(defn- compare-numbers [e1 e2]
  (cond
    (< e1 e2) -1
    (> e1 e2) 1))

(defn- num->vec [e]
  (if (number? e)
    [e] e))

(defn- compare-rows [e1 e2]
  (let [args [e1 e2]]
    (cond
      (every? number? args)
      (compare-numbers e1 e2)

      (some number? args)
      (compare-rows (num->vec e1) (num->vec e2))

      (every? empty? args)
      nil

      (empty? e1) -1
      (empty? e2) 1

      :else
      (if-let [res (compare-rows (first e1) (first e2))]
        res
        (compare-rows (subvec e1 1) (subvec e2 1))))))

(defn- get-compare-result [[s1 :as coll]]
  (when (seq s1)
    (let [[coll1 coll2] (map read-string coll)]
      (compare-rows coll1 coll2))))

(defn process [input]
  (->> input
       (partition-by empty?)
       (remove #{'("")})
       (keep-indexed
         (fn [i coll]
           (when (= (get-compare-result coll) -1)
             (inc i))))))

(defn- sum [coll]
  (reduce + coll))

(defn part-1 [& [data]]
  (-> (or data (util/slurp+split-line "13"))
      process
      sum))

(def ^:const ^:pricate divider-packets
  #{"[[2]]" "[[6]]"})

(defn- score2 [result]
  (reduce
    *
    (keep-indexed
      (fn [i e]
        (when (divider-packets e)
          (inc i)))
      result)))

(defn part-2 [& [data]]
  (->> (or data (util/slurp+split-line "13"))
       (remove empty?)
       (concat divider-packets)
       (sort #(get-compare-result [%1 %2]))
       score2))