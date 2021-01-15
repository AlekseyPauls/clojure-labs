(ns ru.nsu.fit.smp.lab1.task3)

(defn my-map [f coll]
  (apply list (reduce
                (fn [coll x] (conj coll (f x)))
                []
                coll)))

(defn my-filter [pred coll]
  (apply list (reduce (fn [coll x] (if (pred x) (conj coll x) coll))
                      []
                      coll)))
