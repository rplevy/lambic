(defproject lambic "0.1.0"
  :description "Sequence transformations by example."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/core.match "0.2.0-alpha9"]]
  :profiles {:dev {:dependencies [[midje "1.4.0"]]}}
  :plugins [[lein-midje "2.0.0-SNAPSHOT"]])