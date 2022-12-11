(ns advent-of-code.year-2022.day-11.main
  (:require [advent-of-code.util :as util]
            [clojure.string :as str]))

(defn- get-monkey-n [monkey-n-str]
  (->> (str/split monkey-n-str #"Monkey ")
       last
       (remove #{\:})
       (apply str)
       util/str->int))

(defn- remove-not-number-from-str [s]
  (->> s
       (filter #(or (Character/isDigit %) (= \, %)))
       (apply str)))

(defn- get-starting-items [starting-items]
  (mapv
    util/str->int
    (str/split
      (remove-not-number-from-str starting-items)
      #",")))

(defn- get-operation-fn [operation]
  (let [n #(if (Character/isDigit (first %))
              (util/str->int %)
              %2)
        [n1 op n2] (-> operation
                       (str/replace "  Operation: new = " "")
                       (str/split #" "))
        operator (eval (read-string op))]
    (fn [old]
      (operator
       (n n2 old)
       (n n1 old)))))

(defn get-monkey-num [s]
  (util/str->int (remove-not-number-from-str s)))

(defn- test-str->div [test-str]
  (-> test-str
      (str/replace "  Test: divisible by " "")
      util/str->int))

(defn- get-test-fn [num test-true test-false]
  (fn [%]
    (get-monkey-num
      (if (zero? (mod % num))
        test-true
        test-false))))

(defn- get-initial-state [monkeys-data]
  (reduce
    (fn [m [monkey-n starting-items operation test-str test-true test-false]]
      (if (seq monkey-n)
        (assoc m
          (get-monkey-n monkey-n)
          (let [div (test-str->div test-str)]
            {:starting-items (get-starting-items starting-items)
             :div div
             :operation (get-operation-fn operation)
             :test (get-test-fn div test-true test-false)}))
        m))
    {} (partition-by empty? monkeys-data)))

(defn- update-states [state worry-level-fn]
  (loop [state state
         monkey-i 0]
    (if-let [{:keys [operation test]}
             (state monkey-i)]
      (recur
        (loop [state state]
          (if-let [item (get-in state [monkey-i :starting-items 0])]
            (recur
              (let [worry-level (worry-level-fn (operation item))
                    monkey-to (test worry-level)]
                (-> state
                    (update-in
                      [monkey-to :starting-items]
                      conj worry-level)
                    (update-in
                      [monkey-i :starting-items]
                      #(-> % rest vec))
                    (update-in
                      [monkey-i :times-inspecting]
                      #(inc (or % 0))))))
            state))
        (inc monkey-i))
      state)))

(defn- process [rounds monkeys-data worry-level-fn]
  (->> monkeys-data
       (iterate #(update-states % worry-level-fn))
       (drop rounds)
       first))

(defn- score [processed]
  (->> processed
       (map (fn [[_ {:keys [times-inspecting]}]] times-inspecting))
       (sort >)
       (take 2)
       (reduce *)))

(defn part-* [rounds monkeys-data worry-level-fn]
  (score (process rounds monkeys-data worry-level-fn)))

(defn part-1 [& [data]]
  (part-* 20
          (get-initial-state (or data (util/slurp+split-line "11")))
          #(quot % 3)))

(defn part-2 [& [data]]
  (let [initial-state (get-initial-state (or data (util/slurp+split-line "11")))
        div (reduce-kv
              (fn [n _ {:keys [div]}]
                (* div n))
              1 initial-state)]
    (part-* 10000 initial-state #(mod % div))))

(comment
  (part-1)
  (part-2))