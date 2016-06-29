(ns mapreduce.user
  (:use [mapreduce.core]))


; some user defined common functions

(defcmp sort-identity [[k1 v1] [k2 v2]]
        (.compareTo k1 k2))

(defmap map-identity[ k v]
        [[k v]])

(defmap reduce-identity[ k v ]
        (for[ vv v ] [k vv]))

(defmap reduce-values[ k v ]
        [ k v ])

(defmap reduce-singleton[ k v ]
        [k (first v)])


(defcmp desc-by-key [[k1 v1] [k2 v2]]
        (.compareTo k2 k1))

(defcmp desc-by-value [[k1 v1] [k2 v2]]
        (.compareTo v2 v1))
