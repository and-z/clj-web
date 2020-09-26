(ns user)

(defn dev []
  (require 'dev)
  (in-ns 'dev))

(defn go []
  (dev)
  (require 'integrant.repl)
  ((resolve 'integrant.repl/go)))

