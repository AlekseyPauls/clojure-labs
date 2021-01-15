(ns ru.nsu.fit.smp.lab4.core
  (:use ru.nsu.fit.smp.lab4.primitives)
  (:use ru.nsu.fit.smp.lab4.operations)
  (:use ru.nsu.fit.smp.lab4.task1)
  (:use ru.nsu.fit.smp.lab4.utils)
  )

;(defn distinct-by [f coll]
;  (let [groups (group-by f coll)]
;    (println groups)
;    (map #(first (groups %)) (distinct (map f coll)))))

(defn -main
  []

  ;;(println (distinct [(logic-or (variable :x) (variable :x)) , (logic-or (variable :x) (variable :x)), (logic-or (variable :y) (variable :x)), (variable :z)]))

  ;; primitives
  (println "Primitives:")
  (println (expr-to-string (constant true)))
  (println (expr-to-string (variable :x)))
  (println "")

  ;; operations
  (println "Operations:")
  (println (expr-to-string (logic-not (logic-not (variable :x)))))
  (println (expr-to-string (logic-and (variable :x) (variable :y))))
  (println (expr-to-string (logic-or (variable :x) (variable :y))))
  (println (expr-to-string (logic-impl (variable :x) (variable :y))))
  (println "")

  ;; DNF
  (println "DNF:")
  (println "x -> y ==" (expr-to-string (dnf (logic-impl (variable :x) (variable :y)))))
  (println "--x ==" (expr-to-string (dnf (logic-not (logic-not (variable :x))))))
  (println "-(x & y) ==" (expr-to-string (dnf (logic-not (logic-and (variable :x) (variable :y))))))
  (println "x | (x & y) ==" (expr-to-string (dnf (logic-and (variable :x) (logic-or (variable :x) (variable :y))))))
  (println "-((x -> y) | -(y -> z)) ==" (expr-to-string (dnf (logic-not (logic-or
                                                                          (logic-impl (variable :x) (variable :y))
                                                                          (logic-not (logic-impl (variable :y) (variable :z))))))))
  (println "")

  ;; Substitute
  (println "Substitute:")
  (println "(x -> y), x=true ==" (expr-to-string (substitute (logic-impl (variable :x) (variable :y)) (array-map :x true)))))