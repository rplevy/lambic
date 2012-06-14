(ns lambic.test.core
  (:require [lambic.core :refer :all]
            [midje.sweet :refer :all]))

(fact
 "singular structure"
 (deftr foo->bar {:a a :b c} [a c])
 (foo->bar {:a 1 :b 2}) => [1 2])

(facts
 "simplest elipsis"
 
 (deftr simple-elipsis-example
   [{:a a :b b} ...]
   [[   a    b] ...])

 (simple-elipsis-example [{:a 1 :b 2} {:a 3 :b 4}])
 =>                      [[   1    2] [   3    4]]
 (simple-elipsis-example (repeat 1000 {:a 1 :b 2}))
 =>                      (repeat 1000 [   1    2])

 
 (deftr simple-elipsis-example2
   [[a b] ...]
   [[b a] ...])

 (simple-elipsis-example2 [[1 2] [3 4]])
 =>                       [[2 1] [4 3]])


(future-facts
 "manipulate deeper / hierarchical structures"
 (deftr simple-elipsis-example3
   [[a {b c}]     ...]
   [[{a b} {a c}] ...])
 
 (simple-elipsis-example3 [[:foo {:bar :baz}]
                           [:qux {:bzz :zzz}]])
 =>                      [[{:foo :bar} {:foo :baz}]
                          [{:qux :bzz} {:qux :zzz}]])

(future-facts
 "multiple elipses"

 (deftr multiple-example
   [[[{:t t :value value} ...1]
     [{:t t :value value} ...2]] ...]
   [[[[   t        value] ...2]
     [[   t        value] ...1]] ...])

 (multiple-example
  [[[{:t 1 :value 2.0}  {:t 2 :value 3.0}  {:t 3 :value 3.0}]
    [{:t 1 :value 4.0}  {:t 2 :value 5.0}  {:t 3 :value 6.0}]]
   
   [[{:t 1 :value 7.0}  {:t 2 :value 8.0}  {:t 3 :value 9.0}]
    [{:t 1 :value 0.0}  {:t 2 :value -1.0} {:t 3 :value -2.0}]]
   
   [[{:t 1 :value -1.0} {:t 2 :value -2.0} {:t 3 :value -3.0}]
    [{:t 1 :value -4.0} {:t 2 :value -5.0} {:t 3 :value -6.0}]]])
 =>
 [[[[   1        4.0]   [   2         5.0]  [  3         6.0]]
   [[   1        2.0]   [   2         3.0]  [  3         3.0]]]
  
  [[[   1        0.0]   [   2        -1.0]  [  3        -2.0]]
   [[   1        7.0]   [   2         8.0]  [  3         9.0]]]
  
  [[[   1       -4.0]   [   2        -5.0]  [  3        -6.0]]
   [[   1       -1.0]   [   2        -2.0]  [  3        -3.0]]]])