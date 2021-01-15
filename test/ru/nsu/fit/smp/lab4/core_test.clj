(ns ru.nsu.fit.smp.lab4.core-test
  (:require [clojure.test :refer :all])
  (:use [ru.nsu.fit.smp.lab4.utils])
  (:use [ru.nsu.fit.smp.lab4.task1])
  (:use [ru.nsu.fit.smp.lab4.primitives])
  (:use [ru.nsu.fit.smp.lab4.operations]))

(deftest task1-test
  (testing "Test primitives"
    (is (= "x" (expr-to-string (variable :x))))
    (is (= true (variable? (variable :x))))
    (is (= "true" (expr-to-string (constant true))))
    (is (= true (constant? (constant true))))
    (is (= "false" (expr-to-string (constant false))))
    (is (= true (constant? (constant false))))
    )

  (testing "Test operations"
    (is (= "(-x)" (expr-to-string (logic-not (variable :x)))))
    (is (= true (logic-not? (logic-not (variable :x)))))
    (is (= "(x & y)" (expr-to-string (logic-and (variable :x) (variable :y)))))
    (is (= true (logic-and? (logic-and (variable :x) (variable :y)))))
    (is (= "(x | y)" (expr-to-string (logic-or (variable :x) (variable :y)))))
    (is (= true (logic-or? (logic-or (variable :x) (variable :y)))))
    (is (= "(x -> y)" (expr-to-string (logic-impl (variable :x) (variable :y)))))
    (is (= true (logic-impl? (logic-impl (variable :x) (variable :y)))))
    (is (= "(x & y & z)" (expr-to-string (logic-and (variable :x) (variable :y) (variable :z)))))
    (is (= true (logic-and? (logic-and (variable :x) (variable :y) (variable :z)))))
    )

  (testing "Test expressions"
    (is (= "(x & (y -> (x | (-z))))" (expr-to-string (logic-and (variable :x)
                                                    (logic-impl (variable :y)
                                                                (logic-or (variable :x)
                                                                          (logic-not (variable :z))))))))
    )

  (testing "Test implication replacement"
    (is (= "((-x) | y)" (expr-to-string (dnf (logic-impl (variable :x) (variable :y))))))
    )

  (testing "Test double 'not' replacement (involution law)"
    (is (= "x" (expr-to-string (dnf (logic-not (logic-not (variable :x)))))))
    )

  (testing "Test 'not' propagation"
    (is (= "((-x) | (-y))" (expr-to-string (dnf (logic-not (logic-and (variable :x) (variable :y)))))))
    )

  (testing "Test distributive property"
    (is (= "((x & y) | (x & z))" (expr-to-string (dnf (logic-and (variable :x) (logic-or (variable :y) (variable :z)))))))
    )

  (testing "Test absorption law"
    (is (= "x" (expr-to-string (dnf (logic-and (variable :x) (logic-or (variable :x) (variable :y)))))))
    )

  (testing "Test identity law"
    (is (= "x" (expr-to-string (dnf (logic-and (variable :x) (constant true))))))
    (is (= "x" (expr-to-string (dnf (logic-or (variable :x) (constant false))))))
    )

  (testing "Test annihilation law"
    (is (= "true" (expr-to-string (dnf (logic-or (variable :x) (constant true))))))
    (is (= "false" (expr-to-string (dnf (logic-and (variable :x) (constant false))))))
    )

  (testing "Test idempotent law"
    (is (= "x" (expr-to-string (dnf (logic-or (variable :x) (variable :x))))))
    (is (= "x" (expr-to-string (dnf (logic-and (variable :x) (variable :x))))))
    )

  (testing "Test DNF"
    (is (= "((x & (-y)) | (x & (-y) & z))" (expr-to-string (dnf (logic-not (logic-or
                                                 (logic-impl (variable :x) (variable :y))
                                                 (logic-not (logic-impl (variable :y) (variable :z)))))))))
    )

  (testing "Test substitution"
    (is (= "(true -> y)" (expr-to-string (substitute (logic-impl (variable :x) (variable :y)) (array-map :x true)))))
    )
  )

