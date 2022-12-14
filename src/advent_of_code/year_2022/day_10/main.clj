(ns advent-of-code.year-2022.day-10.main
  (:require [advent-of-code.util :as util]
            [clojure.string :as str]))

(def ^:private ^:const cycles-by-act-map
  {"noop" 1
   "addx" 2})

(defn- get-new-status [status [act v] cycle]
  (reduce
    (fn [{:keys [x] :as m} cycle-i]
      (cond->
        (update
          m :cycles
          #(assoc %
             (+ cycle cycle-i)
             x))
        (not= 1 cycle-i)
        (update :x #(+ % (util/str->int v)))))
    status (range 1 (inc (cycles-by-act-map act)))))

(defn get-xs-by-cycle [coll]
  (loop [coll coll
         cycle 0
         status {:cycles {cycle 1}
                 :x 1}]
    (if-let [[act :as line] (some-> (first coll)
                                    (str/split #" "))]
      (recur (rest coll)
             (+ cycle (cycles-by-act-map act))
             (get-new-status status line cycle))
      (:cycles status))))

(defn part-1 [& [data]]
  (let [data (or data (util/slurp+split-line "10"))]
    (reduce-kv
      (fn [s k v] (+ s (* k v)))
      0 (select-keys
          (get-xs-by-cycle data)
          (range 20 221 40)))))

(defn part-2 [& [data]]
  (for [row (partition 40
                       (-> (or data (util/slurp+split-line "10"))
                           get-xs-by-cycle
                           (dissoc 0)
                           sort))]
    (str/join
      (for [[cycle x] row
            :let [pixel (dec (mod cycle 40))]]
        (if (<= (dec pixel) x (inc pixel))
          "#"
          ".")))))

(comment
  (part-1)
  (part-2))