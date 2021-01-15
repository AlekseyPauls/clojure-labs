(ns ru.nsu.fit.smp.lab3.task1)

(defn split-col
  [n col]
  (take-while #(not (empty? %))
              (lazy-seq (cons
                          (take n col)
                          (split-col n (drop n col))))))

(defn parallel-filter
  [pred n col]
  (->> col
       ;(split-col n)
       (split-col (/ (count col) n))
       ;(print)
       (map #(future (doall (filter pred %))))
       (doall)
       (map deref)
       (apply concat)))
