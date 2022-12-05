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
            (<= oth-min range-min range-max oth-max))
          (remove (set interval) elf-data)))
      elf-data)))

(defn count-repeated-assignment-pairs [& [input-data]]
  (->> (or input-data (util/slurp+split-line "04"))
       (filter single-elf-repeated-assignment?)
       count))

(defn- partialy-contains? [[int1-min int1-max] [int2-min int2-max]]
  (or (<= int2-min int1-min int2-max)
      (<= int2-min int1-max int2-max)
      (<= int1-min int2-min int1-max)
      (<= int1-min int2-max int1-max)))

(defn- sinle-elf-overlaping-assigment-pair? [elf-str-data]
  (let [elf-data (str->int-intervals elf-str-data)]
    (some
      (fn [interval]
        (every?
          #(partialy-contains? interval %)
          (remove (set interval) elf-data)))
      elf-data)))

(defn count-overlaping-assigment-pairs [& [input-data]]
  (->> (or input-data (util/slurp+split-line "04"))
       (filter sinle-elf-overlaping-assigment-pair?)
       count))