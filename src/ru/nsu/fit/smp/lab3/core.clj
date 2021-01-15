(ns ru.nsu.fit.smp.lab3.core
  (:use ru.nsu.fit.smp.lab3.task1)
  (:use ru.nsu.fit.smp.lab3.task2)
  (:use ru.nsu.fit.smp.lab3.utils)
  )

(defn -main
  []
  ;(let [col (take 20 (iterate inc -10))]
  ;  (println "Collection:")
  ;  (println col)
  ;  (println "*** Even elements ***")
  ;  (println "Standard filter:")
  ;  (println (filter even? col))
  ;  (println "Parallel filter:")
  ;  (println (parallel-filter even? 4 col))
  ;  (println "Lazy parallel filter:")
  ;  (println (lazy-parallel-filter even? 5 4 col))
  ;  (println "------------------------------------")
  ;  (println "*** Negative elements ***")
  ;  (println "Standard filter:")
  ;  (println (filter neg? col))
  ;  (println "Parallel filter:")
  ;  (println (parallel-filter neg? 4 col))
  ;  (println "Lazy parallel filter:")
  ;  (println (lazy-parallel-filter neg? 2 4 col)))
  ;(println "==============================================")
  (let [col (take 500 (iterate inc 0))]
    (println "Time cost")
    (println "Collection:")
    (println col)
    (println "*** Heavy-even elements (with delay) ***")
    (println "Parallel filter:")
    (time (println (doall (parallel-filter heavy-even? 3 col))))
    (println "Standard filter:")
    (time (println (doall (filter heavy-even? col))))
    (println "Lazy parallel filter:")
    (time (doall (lazy-parallel-filter heavy-even? 2 4 col))))
  (println "==============================================")
  (println "Lazy parallel filter with infinity collection:")
  (doall (lazy-parallel-filter heavy-even? 4 4 (iterate inc 0)))
  )