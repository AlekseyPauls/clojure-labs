(ns ru.nsu.fit.smp.lab1.task4
  (:use ru.nsu.fit.pm.lab1.task3))

(defn extend-strings-map-filter
  "Append one symbols to every string in strings

  @param symbol: char
  @param strings: list of strings
  @return list: list of updated strings"

  [symbol strings]
  (filter (fn [x] (not= (.substring x 0 1) (.substring x 1 2))) (map (fn [x] (.concat symbol x)) strings)))

(defn process-layer-map-reduce
  "Get all valid combinations of strings and symbols (resulting strings have n+1 length)

  @param symbols: list of chars
  @param strings: list of strings
  @return list: list of updated strings"

  [symbols strings]
  (if (= (count strings) 0)
    symbols
    (reduce concat (map (fn [x] (extend-strings-map-filter x strings)) symbols))))


(defn task4
  "Returns all strings of length n consisting of given symbols and not containing adjacent repeated characters

  @param symbols: list of chars
  @param n: length of output strings
  @param res: list of resulting strings
  @return list: resulting strings"

  ([symbols n]
   (task4 symbols n (list)))
  ([symbols n res]
   (if (= n 0)
     res
     (task4 symbols (- n 1) (process-layer-map-reduce symbols res)))))