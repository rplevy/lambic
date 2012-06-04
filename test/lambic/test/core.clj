(ns lambic.test.core
  (:require [lambic.core :refer :all]
            [midje.sweet :refer :all]))

(facts "singular structure"
  (deftr foo->bar {:a a :b c} [a c])
  => truthy
  
  (foo->bar {:a 1 :b 2})
  => [1 2])

(facts "simplest elipsis"
  (deftr fooz->barz
    [{:a a :b b} ...]
    [[a b] ...])
  => truthy

  (fooz->barz [{:a 1 :b 2} {:a 3 :b 4}])
  => [[1 2] [3 4]])