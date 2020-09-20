(ns dev
  (:require [integrant.repl :as ir-repl :refer [go halt reset]]
            [example.backend.core :as backend]))

(ir-repl/set-prep! (constantly (backend/system-config {:http/port 9000, :env :dev})))
