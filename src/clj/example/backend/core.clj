(ns example.backend.core
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]))

(defn respond-hello [_]
  {:status 200 :body "Hello pedestal"})

(def routes
  (route/expand-routes
   #{["/greet" :get respond-hello :route-name :greet]}))

(defn create-server []
  (http/create-server
   {::http/routes routes
    ::http/type :jetty
    ::http/port 9000}))

(defn start []
  (http/start (create-server)))

(defn print-routes []
  (route/print-routes routes))
