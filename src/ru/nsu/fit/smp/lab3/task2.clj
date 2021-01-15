(ns ru.nsu.fit.smp.lab3.task2
  (:require ru.nsu.fit.smp.lab3.task1))

(defn lazy-parallel-filter
  ([pred n col]
   (lazy-parallel-filter pred 4 n col))
  ([pred step n col]
   (->> (partition (* step n) col)
        (map (fn [list]
               (->> (partition step list)
                             (map (fn [x] (future (doall (filter pred x)))))
                             (doall)
                             (map deref)
                    ;(println)
                             )))
        (flatten))))