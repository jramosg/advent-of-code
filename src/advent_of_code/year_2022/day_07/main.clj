(ns advent-of-code.year-2022.day-07.main
  (:require [advent-of-code.util :as util]
            [clojure.string :as str]))

(defn- build-tree
  "Build a tree with all the directories"
  [data]
  (loop [data data
         dir []
         dir-sizes {}
         dirs #{}]
    (if-let [input-line (first data)]
      (cond
        (= "/" input-line)
        (recur (rest data) ["/"] data #{})

        (= input-line "$ cd ..")
        (recur (rest data) (drop-last dir) dir-sizes (conj dirs (drop-last dir)))

        (str/starts-with? input-line "$ cd")
        (recur (rest data) (conj (vec dir) (subs input-line 5)) dir-sizes
               (conj dirs (conj (vec dir) (subs input-line 5))))

        (str/starts-with? input-line "$ ls")
        (recur (rest data) dir dir-sizes dirs)

        (str/starts-with? input-line "dir")
        (recur (rest data) dir dir-sizes dirs)

        :else
        (recur
          (rest data)
          dir
          (update-in dir-sizes dir
                     (fn [e]
                       (let [[size-str file-name] (str/split input-line #" ")]
                         (assoc e file-name (Integer/parseInt size-str)))))
          dirs))
      {:dir-tree dir-sizes
       :dirs dirs})))

(defn- sum-dir [dir subdir-or-size]
  (if (number? subdir-or-size)
    subdir-or-size
    (sum-dir (conj dir) subdir-or-size)))

(defn- sum [ns]
  (reduce + ns))

(defn- fs-size [fs]
  (sum
    (for [[_ v] fs]
      (if (number? v)
        v
        (fs-size v)))))

(defn- build-sizes-coll [dirs fs fn>=]
  (keep
    (fn [dir]
      (let [size (fs-size (get-in fs dir))]
        (when (fn>= size) size)))
    dirs))

(defn part-1 [& [data]]
  (let [{:keys [dir-tree dirs]}
        (build-tree (or data (util/slurp+split-line "07")))]
    (sum
      (build-sizes-coll dirs dir-tree #(>= 100000 %)))))

(defn part-2 [& [data]]
  (let [{:keys [dir-tree dirs]} (build-tree (or data (util/slurp+split-line "07")))
        used (fs-size dir-tree)]
    (-> (build-sizes-coll dirs dir-tree #(>= % (- used 40000000)))
        sort
        first)))
