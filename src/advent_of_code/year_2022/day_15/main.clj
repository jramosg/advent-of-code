(ns advent-of-code.year-2022.day-15.main
  (:require [advent-of-code.util :as util]
            [clojure.set :as set]
            [clojure.string :as str]))

(defn- coll->cord [coll]
  (mapv #(-> % peek util/str->int) coll))
(defn- parse-row [row]
  (let [coll (-> (reduce
                   #(str/replace %1 %2 "")
                   row ["Sensor at " "closest beacon is at" ": " ","])
                 (str/split #" ")
                 (as-> coll (mapv #(str/split % #"=") coll)))]
    {:sensor (coll->cord (subvec coll 0 2))
     :beacon (coll->cord (subvec coll 2 4))}))

(defn- dist1 [e1 e2]
  (Math/abs (- e1 e2)))

(defn- dist [[x1 y1] [x2 y2]]
  (+ (dist1 x1 x2)
     (dist1 y1 y2)))

(defn- between [x y z]
  (or (>= x y z)
      (<= x y z)))

(defn- overlap [[x y :as sensor] [bx by :as beacon] target-y]
  (let [d (dist sensor beacon)]
    (when (between (- y d) target-y (+ y d))
      (let [r (- d (dist1 target-y y))]
        (if (= target-y by)
          (disj (set (range (- x r) (+ x r 1)))
                bx)
          (set (range (- x r) (+ x r 1))))))))

(defn process [y input]
  (count
    (reduce
      (fn [s {:keys [sensor beacon]}]
        (set/union s (overlap sensor beacon y)))
      #{} input)))

(defn part-1 [y & [data]]
  (let [data (or data (util/slurp+split-line "15"))
        sensors+beacons (map parse-row data)]
    (process y sensors+beacons)))

(defn brange [[x y :as sensor] beacon target-y]
  (let [d (dist sensor beacon)]
    (when (between (- y d) target-y (+ y d))
      (let [r (- d (dist1 target-y y))]
        [(- x r) (+ x r)]))))

(defn- get-spans [input y]
  (keep (fn [measure]
          (let [{:keys [sensor beacon]} measure
                span (brange sensor beacon y)]
            (when span
              span)))
        input))

(defn find-gap [in-ranges]
  (let [sorted (sort in-ranges)]
    (loop [[l1 l2] (first sorted)
           ranges (rest sorted)]
      (when (seq ranges)
        (let [[r1 r2] (first ranges)]
          (if (> r1 (inc l2))
            (inc l2)
            (recur [(min l1 r1) (max l2 r2)]
                   (rest ranges))))))))

(defn- tunning-freq [x y]
  (+ y (* x 4000000)))

(defn part-2 [& [data]]
  (let [data (or data (util/slurp+split-line "15"))
        sensors+beacons (map parse-row data)]
    (some
      #(some-> (find-gap (get-spans sensors+beacons %))
               (tunning-freq %))
      (range 0 4000001))))
