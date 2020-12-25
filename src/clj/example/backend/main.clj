(ns example.backend.main
  (:require [example.backend.core :as core]
            [integrant.core :as ig])
  (:gen-class))

(defn -main []
  (ig/init (core/config {:port 9000})))
