(ns ru.nsu.fit.smp.lab4.operations)

;; Operations

(defn logic-and
  "Constructor for conjunctions (logic 'and', &) operation"
  [expr & rest]
  (if (empty? rest)
    expr
    (cons ::and (cons expr rest))))

(defn logic-and?
  "Check if expression is conjunction"
  [expr] (= (first expr) ::and))

(defn logic-or
  "Constructor for disjunction (logic 'or', |) operation"
  [expr & rest]
  (if (empty? rest)
    expr
    (cons ::or (cons expr rest))))

(defn logic-or?
  "Check if expression is disjunction"
  [expr] (= (first expr) ::or))

(defn logic-not
  "Constructor for not operation"
  [expr] (list ::not expr))

(defn logic-not?
  "Check if expression is 'not'"
  [expr] (= (first expr) ::not))

(defn logic-impl
  "Constructor for implication operation"
  [expr-x expr-y] (list ::impl expr-x expr-y))

(defn logic-impl?
  "Check if expression is implication"
  [expr] (= (first expr) ::impl))