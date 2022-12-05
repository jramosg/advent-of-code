(ns advent-of-code.year-2022.day-04.main
  (:require [advent-of-code.util :as util]
            [clojure.string :as str]))

(defn- read-dashed-interval [s]
  (map
    #(Integer/parseInt %)
    (str/split s #"-")))

(defn str->int-intervals [elf-str-data]
  (map
    read-dashed-interval
    (str/split elf-str-data #",")))

(defn single-elf-repeated-assignment? [elf-str-data]
  (let [elf-data (str->int-intervals elf-str-data)]
    (some
      (fn [[range-min range-max :as interval]]
        (every?
          (fn [[oth-min oth-max]]
            (and (<= oth-min range-min oth-max)
                 (<= oth-min range-max oth-max)))
          (remove (set interval) elf-data)))
      elf-data)))

(defn count-repeated-assignment-pairs [& [input-data]]
  (->> (or input-data (util/slurp+split-line "04"))
       (filter single-elf-repeated-assignment?)
       count))

(defn str->range-intervals [elf-str-data]
  (map
    (fn [s]
      (let [[start end] (read-dashed-interval s)]
        (range start (inc end))))
    (str/split elf-str-data #",")))

(defn- sinle-elf-overlaping-assigment-pair? [elf-str-data]
  (->> (str->range-intervals elf-str-data)
       flatten
       frequencies
       (some (fn [[_ f]] (> f 1)))))

(defn count-overlaping-assigment-pairs [& [input-data]]
  (->> (or input-data (util/slurp+split-line "04"))
       (filter sinle-elf-overlaping-assigment-pair?)
       count))