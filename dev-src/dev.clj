(ns dev
  (:require [integrant.repl :as ir-repl :refer [go halt reset]]
            [example.backend.core :as backend]))

(defn dev-system []
  (backend/system-config {:http/port 9000, :env :dev}))

(ir-repl/set-prep! dev-system)
