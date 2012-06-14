# lambic

Sequence transformations by example.

Inherits all capabilities of core.match, but extends this with the ability to 
build sequence transformers using elipsis notation. 

I don't know how general this can be made to be but I'm naively experimenting
with various ideas right now.  I've just defined a basic macro that understands
a simple elipsis and expands to a mapping.  The (ambitious? easy?) goal is fully
general declarative (and oh yeah, performant) compilation of sequence 
transformers defined using an intuitive elipsis notation.

![lambic](https://raw.github.com/rplevy/lambic/master/etc/lambic.png)

## Usage

### Create a Transformer Function
 
```
(tr <pattern-in> <pattern-out>)
```

### Define a Transformer Function

```
(deftr <function-name> <pattern-in> <pattern-out>)
```

### Pattern Specification

The non-elipsis case is itself very powerful; that is the power of core.match / core.logic (at best I've added a thin layer of syntax around it).

```
(deftr some-thing->other-thing
  {:a [x] :b [y]}
  {x {:s y}})

(some-thing->other-thing {:a [:q] :b [2]})   =>  {:q {:s 2}}
```

Specifying a sequence transformation involves the ```...``` symbol.

```
(deftr fooz->barz
  [{:a a :b c} ...]
  [[a c] ...])

(fooz->barz [{:a 1 :b 2} {:a 3 :b 4}])  =>  ([1 2] [3 4]))
```

### Future Work / Next Steps

I'm not sure how easy or hard this will be, but if it is easy it will probably
be because core.match has already done most of theoretical/applied magic for me.
I would like to be able to use elipses notation in a more general way on all 
sorts of complex structures. For example, something like this should be possible:

```
(deftr raw-data->new-format
  {uuid [{_ _ :ts ts :value value}
         ...]
   ...}

  [{:uuid uuid
    :data [{:ts ts :value value}
           ...]}
   ...])

(raw-data->new-format
  {"123"
   [{:uuid "123", :timestamp "1293883200000", :value "1.0"}
    {:uuid "123", :timestamp "1293884100000", :value "2.0"}
    {:uuid "123", :timestamp "1293969600000", :value "3.0"}
    {:uuid "123", :timestamp "1293970500000", :value "4.0"}],
   "321"
   [{:uuid "321", :timestamp "1293883200000", :value "1.2"}
    {:uuid "321", :timestamp "1293884100000", :value "2.2"}
    {:uuid "321", :timestamp "1293969600000", :value "3.2"}
    {:uuid "321", :timestamp "1293970500000", :value "4.2"}]})

  =>

  [{:uuid "321",
    :data
    [{:timestamp "1293883200000", :value "1.2"}
     {:timestamp "1293884100000", :value "2.2"}
     {:timestamp "1293969600000", :value "3.2"}
     {:timestamp "1293970500000", :value "4.2"}]}
   {:uuid "123",
    :data
    [{:timestamp "1293883200000", :value "1.0"}
     {:timestamp "1293884100000", :value "2.0"}
     {:timestamp "1293969600000", :value "3.0"}
     {:timestamp "1293970500000", :value "4.0"}]}]
```

## License

Copyright (C) 2012 Robert P. Levy

Distributed under the Eclipse Public License, the same as Clojure.
