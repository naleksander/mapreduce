(ns mapreduce.examples
  (:use [mapreduce.user])
        (:use [mapreduce.core]))

; sample input data

(def data-input   [ [ 0 "hello world" ] [ 1 "hello kitty"]
                   [ 2 "hello map reduce"]
                   [ 3 "map reduce job"] [ 4 "already hello done job"]
                   [ 5 "map your map to the map"] ])

; example 1
; count words occurences and sort desc by most common

(defmap count-words [k v]
        (map (fn[a] [ a 1 ]) (.split v "\\s+")))


(defreduce sum-word-occurences [ k v ]
           [k (reduce + 0 v)])

(->>

  data-input

  (map-reduce count-words sum-word-occurences sort-identity)

  (map-reduce map-identity reduce-singleton desc-by-value  ))


; example 2
; inverted index

(defmap word-occurences [k v]
        (map (fn[a] [ a k ]) (.split v "\\s+")))

(defreduce index-words [ k v ]
           [k (sort (into #{} v))])

(->>

  data-input

  (map-reduce word-occurences index-words sort-identity))


; example 3
; grep

(defmap grep-word[a]
        (fn[ k v]
          (if (re-matches (re-pattern (str ".*" a ".*")) v)
            [[k v]]
            )))

(->>

  data-input

  (map-reduce (grep-word "done") reduce-values sort-identity))


; example 4
; longest sentence

(defmap count-characters [k v]
        [[ v (.length v) ]])

(defreduce emit-only-key [ k v] k )


(defcmp sort-value-desc-then-key-asc
        [ [k1 v1] [k2 v2] ]
        (let[ c (.compareTo v2 v1)]
          (if (= c 0)
            (.compareTo k1 k2)
            c)))

(->>

  data-input

  (map-reduce count-characters emit-only-key sort-value-desc-then-key-asc))


; example 5
; to upper case

(defmap to-upper-case [k v]
        [[k (.toUpperCase v)]])

(->>

  data-input

  (map-reduce to-upper-case reduce-singleton sort-identity))


; example 6
; common friends for given pair of people basing on list
; of friends for each person of that pair
;
; so for example when :C visits :B profile he can find out
; that :A :D :E are common friends

(def friends-input  { :A [ :B :C :D ]
                     :B [ :A :C :D :E ]
                     :C [ :A :B :D :E :F ]
                     :D [ :A :B :C :E ]
                     :E [ :B :C :D ]
                     :F [ :C ]})


(defmap pair-friends[ k  v ]
        (for [ vv v ] [ (vec (sort [k vv])) (set v) ]))


(defreduce intersect-friends[ k v ]
    [ k (vec (sort (apply clojure.set/intersection v))) ])

(->>
  friends-input

  (map-reduce pair-friends intersect-friends sort-identity))

