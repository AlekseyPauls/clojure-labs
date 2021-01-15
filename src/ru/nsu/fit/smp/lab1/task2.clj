(ns ru.nsu.fit.smp.lab1.task2)

(defn extend-strings-recurrent
  "Append one symbols to every string in strings and put into res

  @param symbol: char
  @param strings: list of strings
  @param res: list of updated strings with accumulate result between calls
  @return list: list of updated strings"

  ([symbol strings]
   (extend-strings-recurrent symbol strings (list)))
  ([symbol strings res]
   (if (= (count strings) 0)
     res
     (if (= symbol (.substring (first strings) 0 1))
       (recur symbol (rest strings) res)
       (recur symbol (rest strings) (concat res (list (.concat symbol (first strings)))))
       ))))

(defn process-layer-recurrent
  "Get all valid combinations of strings and symbols and put it into res (resulting strings have n+1 length)

  @param symbols: list of chars
  @param strings: list of strings
  @param res: list of updated strings with accumulate result between calls
  @return list: list of updated strings"

  ([symbols strings]
   (process-layer-recurrent symbols strings (list)))
  ([symbols strings res]
   (cond
     (= (count strings) 0) symbols
     (= (count symbols) 0) res
     (> (count symbols) 0) (recur (rest symbols) strings
                             (concat res (extend-strings-recurrent (first symbols) strings))))))

(defn task2
  "Returns all strings of length n consisting of given symbols and not containing adjacent repeated characters

  @param symbols: list of chars
  @param n: length of output strings
  @param res: list of resulting strings
  @return list: resulting strings"

  ([symbols n]
   (task2 symbols n (list)))
  ([symbols n res]
   (if (= n 0)
     res
     (recur symbols (- n 1) (process-layer-recurrent symbols res)))))
