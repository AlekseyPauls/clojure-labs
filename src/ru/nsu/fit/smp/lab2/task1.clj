(ns ru.nsu.fit.smp.lab2.task1
  (:require [ru.nsu.fit.pm.lab2.utils :refer :all]))


(defn round
  [^double d ^long precision]
  (let [factor (Math/pow 10 precision)]
    (/ (Math/round (* d factor)) factor)))

(defn partial-sum
  [func step left right]
  (if (> right left)
    (+ (partial-sum func step left (round (- right step) 5) ) (func right))
    0))

(def partial-sum-memoized
  (memoize (fn [func step left right]
             (do
               ;(println left right)
               (if (> right left)
                 (+ (partial-sum-memoized func step left (round (- right step) 5)) (func right))
                 0)
               )
             )))

(defn get-integral-function
  ([func]
   (get-integral-function func 0.1))
  ([func step]
   (fn [x]
     (let [out (mod x step)                                ;; Значение, выходящее за пределы сетки
           n (quot x step)
           result (* step
                     (+ (/ (+ (func 0) (func x)) 2)
                        (partial-sum func step 0.0 x)))]
       (if (= 0.0 out)
         result
         (+ result (trapezia-area out (func n) (func out))))))))

(defn get-memoized-integral-function
  ([func]
   (get-memoized-integral-function func 0.1))
  ([func step]
   (fn [x]
     (let [out (mod x step)                                ;; Значение, выходящее за пределы сетки
           n (quot x step)
           result (* step
                     (+ (/ (+ (func 0) (func x)) 2)
                        (partial-sum-memoized func step 0.0 x)))]
       (do
         ;(println out)
         (if (= 0.0 out)
           result
           (+ result (trapezia-area out (func n) (func out))))
         )
       ))))



"""
Map-reduce variant
"""

;(defn partial-sum
;  [func step left right]
;  (let [n (int (/ (- right left) step))]
;  (reduce +
;          (map func
;               (map (fn [y] (double (* y step)))
;                    (range 1 n)))
;          )))
;
;(def partial-sum-memoized (memoize partial-sum))
;
;(defn get-integral-function
;([func]
; (get-integral-function func 0.001))
;([func step]
; (fn [x]
;     (* step
;        (+ (/ (+ (func 0) (func x)) 2)
;           (partial-sum func step 0 x))))))
;
;(defn get-memoized-integral-function
;  ([func]
;   (get-memoized-integral-function func 0.001))
;  ([func step]
;   (fn [x]
;     (* step
;        (+ (/ (+ (func 0) (func x)) 2)
;           (partial-sum-memoized func step 0 x))))))