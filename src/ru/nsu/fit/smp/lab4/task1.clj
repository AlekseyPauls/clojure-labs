(ns ru.nsu.fit.smp.lab4.task1
  (:use ru.nsu.fit.smp.lab4.primitives)
  (:use ru.nsu.fit.smp.lab4.operations)
  (:use ru.nsu.fit.smp.lab4.utils))

;; Rules

(defn decompose
  "'(a & (b & c))' ---> '(a & b & c)'"
  [expr]
  (if (and (not (primitive? expr))
           (or
             (logic-or? expr)
             (logic-and? expr))
           )
    (update-args expr (map #(decompose %) (collect-args expr)))
    expr))

(defn replace-impl
  "Transform all implication in '-A | B'"
  [expr]
  (if (logic-impl? expr)
    (logic-or
      (logic-not (first (rest expr)))
      (last (rest expr)))
    expr))

(defn not-propagation-and-involution
  "Propagate not into brackets and remove double not"
  [expr]
  (if (logic-not? expr)
    (let [arg (first (args expr))]
      (cond
        (logic-not? arg) (first (args arg))
        (constant? arg) (if (= arg (constant true)) (constant false) (constant true))
        (logic-and? arg) (apply logic-or (map (fn [x] (logic-not x)) (args arg)))
        (logic-or? arg) (apply logic-and (map (fn [x] (logic-not x)) (args arg)))
        :else expr))
    expr))

(defn distributive-property
  "Apply distributive property"
  [expr]
  (if (logic-and? expr)
    (let [a (first (args expr)) b (last (args expr))]
      (cond
        (logic-or? a) (logic-or (logic-and b (first (args a))) (logic-and b (last (args a))))
        (logic-or? b) (logic-or (logic-and a (first (args b))) (logic-and a (last (args b))))
        :else expr))
    expr))

(defn identity-and-annihilation-laws
  "(x & true) == x, (x | false) == x, (x & false) == false, (x | true) == true"
  [expr]
  (let [expr (decompose expr)]
    (if (primitive? expr)
      expr
      (cond
        (and (logic-and? expr) (args-contains-const? expr (constant false))) (constant false)
        (and (logic-and? expr) (args-contains-const? expr (constant true))) (apply logic-and (filter #(not (= % (constant true))) (args expr)))
        (and (logic-or? expr) (args-contains-const? expr (constant true))) (constant true)
        (and (logic-or? expr) (args-contains-const? expr (constant false))) (apply logic-or (filter #(not (= % (constant false))) (args expr)))
        :else expr))))

(defn idempotency-law
  "(a & a) == a, (a | a) == a"
  [expr]
  (let [expr (decompose expr)]
    (if (primitive? expr)
      expr
      (cond
        (and (logic-and? expr)) (apply logic-and (distinct (args expr)))
        (and (logic-or? expr)) (apply logic-or (distinct (args expr)))
        :else expr)
      )
    ))

(defn parametrized-absorption [inner-check outer-op next-normalize expr]
  "Util function for absorption law"
  (let [lexpr (first (args expr))
        rexpr (second (args expr))]
    (cond
      (inner-check rexpr)
        (if (or (= lexpr (first (args rexpr))) (= lexpr (second (args rexpr))))
          (next-normalize lexpr)
          (outer-op (next-normalize lexpr) (next-normalize rexpr)))
      (inner-check lexpr)
        (if (or (= rexpr (first (args lexpr))) (= lexpr (second (args lexpr))))
          (next-normalize rexpr)
          (outer-op (next-normalize lexpr) (next-normalize rexpr)))
      :else
        (outer-op (next-normalize lexpr) (next-normalize rexpr)))))

(defn absorption-law
  "Absorption law, (x & (x | y)) == x, (x | (x & y)) == x"
  [expr]
  (cond
    (or (variable? expr) (constant? expr))
      expr
    (logic-and? expr)
      (parametrized-absorption logic-or? logic-and absorption-law expr)
    (logic-or? expr)
      (parametrized-absorption logic-and? logic-or absorption-law expr)
    :else
      expr))

;; Main functions - values substitution and DNF transformation

(defn substitute
  "Substitute variables to expression"
  [expr var-map]
  (apply-recur identity-and-annihilation-laws
               (cond
                 (variable? expr) (let [key (first (args expr))]
                                    (if (contains? var-map key)
                                      (constant (get var-map key))
                                      expr))
                 (constant? expr) expr
                 :else (cons (first expr) (map (fn [expr] (substitute expr var-map)) (args expr))))))

(defn dnf [expr]
  "Transform to DNF"
  (->> expr
       ;; 1 step - replacement of non-basic operations
       (apply-recur replace-impl)
       ;; Add "replace" rules for new operations there
       ;; 2 step - passing "not" into expressions
       ;; 3 step - replacing multiple negatives with one
       (apply-recur not-propagation-and-involution)
       ;; 4 step - application of distributive property and absorption law
       (absorption-law)
       (apply-recur distributive-property)
       ;; 5 step - identity, annihilation and idempotency
       (apply-recur identity-and-annihilation-laws)
       (apply-recur idempotency-law)
       ))
