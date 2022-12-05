(ns advent_of_code.year-2022.day-02.main
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [advent-of-code.util :as util]))

(def ^:const ^:private data-path "src/practicalli/year_2022/day_02/input.txt")

(def ^:const ^:private desencrypt-map
  {\A :rock
   \B :paper
   \C :scissors
   \X :rock
   \Y :paper
   \Z :scissors})

(def ^:const ^:private shape-puntuation-map
  {:rock 1
   :paper 2
   :scissors 3})

(def ^:const ^:private result-puntuation-map
  {:lost 0
   :draw 3
   :win 6})

(def ^:const ^:private winned-by
  {:rock :paper
   :paper :scissors
   :scissors :rock})

(defn get-result [opponent-shape my-shape]
  (cond
    (= opponent-shape my-shape) :draw
    (= (get winned-by opponent-shape) my-shape) :win
    :else :lost))

(defn- get-single-result [[opponent-play _ my-play]]
  (let [my-shape (get desencrypt-map my-play)
        result (get-result (get desencrypt-map opponent-play)
                           my-shape)]
    (+ (get result-puntuation-map result)
       (get shape-puntuation-map my-shape))))

(defn- get-score [single-result-fn & [guide]]
  (->> (or guide (util/slurp+split-line "02"))
       (map single-result-fn)
       (apply +)))

(defn get-final-score [& [guide]]
  (get-score get-single-result guide))

(def ^:const ^:private result-desencryption-map
  {\X :lost
   \Y :draw
   \Z :win})

(defn- get-my-shape [result opponent-shape]
  (case result
    :draw opponent-shape
    :win (get winned-by opponent-shape)
    (get (set/map-invert winned-by) opponent-shape)))

(defn- get-single-result-stategy-2 [[opponent-play _ result-enc]]
  (let [result (get result-desencryption-map result-enc)
        my-shape (get-my-shape result (get desencrypt-map opponent-play))]
    (+ (get result-puntuation-map result)
       (get shape-puntuation-map my-shape))))

(defn score-startegy-2 [& [guide]]
  (get-score get-single-result-stategy-2 guide))