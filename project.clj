(defproject mapreduce "0.1.0-SNAPSHOT"
  :description "Google's Map Reduce"
  :url "https://github.com/naleksander/mapreduce"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot mapreduce.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
