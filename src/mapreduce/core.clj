(ns mapreduce.core)

; core of map reduce

(defmacro defmap
  [ name f & b ]
  `(def ~name (fn ~f ~@b)))

(defmacro defreduce [ & b]
  `(defmap ~@b))

(defmacro defcmp [ & b ]
  `(defmap ~@b))

(defn map-reduce
  [ mapf reducef sortf input ]
  (map (fn[p] (reducef (ffirst p) (map second p)))
       (partition-by first (sort-by identity sortf (mapcat #(apply mapf %) input)))))


