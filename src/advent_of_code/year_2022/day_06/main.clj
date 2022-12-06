(ns advent-of-code.year-2022.day-06.main)

(def ^:const ^:private input-path "src/advent_of_code/year_2022/day_06/input.txt")

(defn- find-i [i data str-end-inc-i]
  (let [str-end (+ i str-end-inc-i)]
    (when (->> (subs data i str-end)
               frequencies
               (every? (fn [[_ f]] (= f 1))))
      str-end)))

(defn- pos-finder [str-end-inc-i & [input-data]]
  (let [data (or input-data (slurp input-path))]
    (->> data
         count
         range
         (some #(find-i % data str-end-inc-i)))))

(defn part-1 [& [input-data]]
  (pos-finder 4 input-data))

(defn part-2 [& [input-data]]
  (pos-finder 14 input-data))

#_(do
    (prn "part 1" (part-1))
    (prn "part 2 " (part-2)))