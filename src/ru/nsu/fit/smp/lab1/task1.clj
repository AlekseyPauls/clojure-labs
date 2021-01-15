(ns ru.nsu.fit.smp.lab1.task1)

(defn extend-strings
  "Append one symbol to every string in strings

  @param symbol: char
  @param strings: list of strings
  @return list: list of updated strings"

  [symbol strings]
  (if (= (count strings) 0)
    (list)
    (if (= symbol (.substring (first strings) 0 1))
      (extend-strings symbol (rest strings))
      (concat
        (list (str symbol (first strings)))
        (extend-strings symbol (rest strings))))))

(defn process-layer
  "Get all valid combinations of strings and symbols (resulting strings have n+1 length)

  @param symbols: list of chars
  @param strings: list of strings
  @return list: list of updated strings"

  [symbols strings]
  (cond
    (= (count strings) 0) symbols
    (= (count symbols) 0) (list)
    (> (count symbols) 0) (concat
                            (extend-strings (first symbols) strings)
                            (process-layer (rest symbols) strings))))

(defn task1
  "Returns all strings of length n consisting of given symbols and not containing adjacent repeated characters

  @param symbols: list of chars
  @param n: length of output strings
  @param res: list of resulting strings
  @return list: resulting strings"

  ([symbols n]
   (task1 symbols n (list)))
  ([symbols n res]
   (if (= n 0)
     res
     (task1 symbols (- n 1) (process-layer symbols res)))))
