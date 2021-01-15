(ns ru.nsu.fit.smp.lab4.utils
  (:use ru.nsu.fit.smp.lab4.primitives)
  (:use ru.nsu.fit.smp.lab4.operations))

(defn args
  "Get arguments of expression. Using often"
  [expr]
  (rest expr))

(defn same-type?
  "Check for expr1 and expr2 are same type"
  [expr1 expr2]
  (= (first expr1) (first expr2)))

(defn primitive?
  "Check for primitive (variable or constant)"
  [expr]
  (or (variable? expr) (constant? expr)))

(defn update-args [expr new-args]
  "Get expression with new arguments"
  (if (> (count new-args) 1)
    (cons (first expr) new-args)
    (list (first expr) (first new-args))))

(defn collect-args
  "Collect argument of same type from same level of expression. Needs to decomposition"
  [expr]
  (if (primitive? expr)
    (list expr)
    (->> (args expr)
         (map (fn [arg] (if (same-type? expr arg)
                          (collect-args arg)
                          (list arg))))
         (apply concat))))

(defn args-contains-const?
  "Check for arguments of operation contains constant"
  [expr const]
  (some #(and (constant? %)
              (= const %))
        (args expr)))

(defn apply-recur
  "Recursively apply function to expression"
  [f expr]
  (let [new-expr (f expr)]
    (cond
      (nil? new-expr) nil
      (or (variable? new-expr) (constant? new-expr)) new-expr
      :else (update-args new-expr (map #(apply-recur f %) (args new-expr))))))

(defn expr-to-string
  "Suitable form for printing expressions"
  [expr]
  (cond
    (variable? expr) (name (first (args expr)))
    (constant? expr) (str (first (args expr)))
    (logic-not? expr) (str "(" (str "-" (expr-to-string (first (args expr)))) ")")
    (logic-or? expr) (str "(" (reduce #(str %1 " | " (expr-to-string %2)) (expr-to-string (first (args expr)))
                                      (rest (args expr))) ")")
    (logic-and? expr) (str "(" (reduce #(str %1 " & " (expr-to-string %2)) (expr-to-string (first (args expr)))
                                       (rest (args expr))) ")")
    (logic-impl? expr) (str "(" (str (expr-to-string (first (args expr))) " -> " (expr-to-string (second (args expr)))) ")")))