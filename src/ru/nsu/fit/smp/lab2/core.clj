(ns ru.nsu.fit.smp.lab2.core
  (:use ru.nsu.fit.smp.lab2.task1)
  (:use ru.nsu.fit.smp.lab2.task2)
  )

(defn func
  [x]
  (* x (* x (* x (* x (* x x)))))
  )

(defn -main
  []
  (println "Single run\nNot memoized:")
  (time ((get-integral-function func) 5.0))
  (time ((get-integral-function func) 10.0))
  (time ((get-integral-function func) 15.0))
  (time ((get-integral-function func) 7.0))
  (time ((get-integral-function func) 10.0))
  (println "Memoized:")
  (time ((get-memoized-integral-function func) 5.0))
  (time ((get-memoized-integral-function func) 10.0))
  (time ((get-memoized-integral-function func) 15.0))
  (time ((get-memoized-integral-function func) 7.0))
  (time ((get-memoized-integral-function func) 10.0))
  (println "----------------------------------\nMany runs\nNot memoized")
  (time
    (->>
      (take 30 (range))
      (reverse)
      (map (get-integral-function func))
      (reduce +)))
  (println "Memoized")
  (time
    (->>
      (take 30 (range))
      (reverse)
      (map (get-memoized-integral-function func))
      (reduce +)))
  (println "----------------------------------\nTask2, lazy seq:")
  (time ((get-lazy-integral-function func) 5.0))
  (time ((get-lazy-integral-function func) 10.0))
  (time ((get-lazy-integral-function func) 15.0))
  (time ((get-lazy-integral-function func) 7.0))
  (time ((get-lazy-integral-function func) 10.0))

  )