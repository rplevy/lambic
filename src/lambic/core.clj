(ns lambic.core
  (:use [clojure.core.match :only [match]]))

(defmacro tr
  "Creates a transformer function mapping a source form to a target form."
  [a-form b-form]
  (match a-form
    [f '...]
    (match b-form
      [g '...]
      `(let [tr-fn# (tr ~(first a-form)
                        ~(first b-form))]
         (fn [input#]
           (map tr-fn# input#)))
      :else (throw (Exception. "Assymetrical elipsis error.")))
    :else
    `(fn [input#]
       (match [input#]
         [~a-form]
         ~b-form))))

(defmacro deftr
  "Defines a transformer function mapping a source form to a target form.
   Inherits all capabilities of core.match, but extends this with compil-
   ation of elipsis forms into sequence transformations."
  [function-name a-form b-form]
  `(def ~function-name (tr ~a-form ~b-form)))
