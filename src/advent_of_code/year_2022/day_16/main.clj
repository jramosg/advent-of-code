(ns advent-of-code.year-2022.day-16.main
  (:require [advent-of-code.util :as util]
            [clojure.string :as str]))

(defn- parse-row [row]
  (let [[_ valve rate leads] (some #(re-matches % row)
                                   [#"Valve (.*) has flow rate=(-?\d+); tunnels lead to valves (.*)"
                                    #"Valve (.*) has flow rate=(-?\d+); tunnel leads to valve (.*)"])]
    {valve {:rate (util/str->int rate)
            :leads (str/split leads #", ")}}))

(defn simple-moves [valves state minute]
  (let [[[from open] score] state]
    (->>
      (reduce
        (fn [m lead] (assoc m [lead open] score))
        {} (get-in valves [from :leads]))
      (merge
        (when (and (not (open from))
                   (pos? (get-in valves [from :rate])))
          {[from (conj open from)]
           (+ score (* (dec minute)
                       (get-in valves [from :rate])))})))))

(defn one-minute [valves states minute]
  (apply
    merge-with max
    (map #(simple-moves valves % minute) states)))

(defn part-1 [& [data]]
  (let [data (or data (util/slurp+split-line "16"))
        valves-data (reduce #(conj %1 (parse-row %2)) {} data)]
    (loop [states {["AA" #{}] 0}
           minute 30]
      (if (= minute 0)
        (apply max (vals states))
        (recur (one-minute valves-data states minute)
               (dec minute))))))