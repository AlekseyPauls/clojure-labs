(ns ru.nsu.fit.smp.lab3.utils)

(defn heavy-even? [x] (do (Thread/sleep 10) (even? x)))
