(ns ru.nsu.fit.smp.lab1.core
  (:use ru.nsu.fit.smp.lab1.task1)
  (:use ru.nsu.fit.smp.lab1.task2)
  (:use ru.nsu.fit.smp.lab1.task4))

(defn -main
  []
  (println (task4 `("a" "b" "c") 4)))
