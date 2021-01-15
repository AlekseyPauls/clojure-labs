(ns ru.nsu.fit.smp.lab2.utils)

(defn abs
  [x]
  (if (> x 0) x (* -1 x)))

(defn round-equality
  ([x y]
   (round-equality x y 0.001))
  ([x y t]
   (if (> (abs (- x y)) t) false true)))

(defn trapezia-area [h left right]
  (* (+ left right) 0.5 h))