(ns ru.nsu.fit.smp.lab4.primitives)

;; Primitives

(defn constant
  "Constructor for constant"
  [bool]
  {:pre [(or (= false bool) (= true bool))]}
  (list ::const bool))

(defn constant?
  "Check if expr is constant"
  [expr]
  (= ::const (first expr)))

(defn variable
  "Constructor for variable"
  [name]
  {:pre [(keyword? name)]}
  (list ::var name))

(defn variable?
  "Check if type expr is variable"
  [expr]
  (= ::var (first expr)))
