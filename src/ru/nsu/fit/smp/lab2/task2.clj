(ns ru.nsu.fit.smp.lab2.task2
  (:require [ru.nsu.fit.pm.lab2.utils :refer :all]))

(defn lazy-partial-sum [seq]
  (def sums (lazy-seq
              (cons (first seq) (map + (next seq) sums))))
  sums)

(defn get-lazy-integral-function
  [func]
  (let [step 0.01
        left 0
        fvals (map func (iterate (fn [x] (+ x step)) (- left step)))
        partial-sums (map (partial trapezia-area step) fvals (rest fvals))
        integrals (lazy-partial-sum partial-sums)]
    (fn [x]
      (let [out (mod x step)                                ;; Значение, выходящее за пределы сетки
            n (quot x step)
            result (nth integrals n)]
        (if (= 0 out)
          result
          (+ result (trapezia-area out (func n) (func out))))))))
