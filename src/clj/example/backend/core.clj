(ns example.backend.core
  (:require [io.pedestal.http :as server]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.ring-middlewares :as middlewares]
            [integrant.core :as ig]))

(defn respond-hello
  [{:request/keys [username]}]
  {:status 200 :body (str "Hello " username)})

(defn routes [interceptors]
  #{["/greet" :get (conj interceptors respond-hello) :route-name :greet]})

(defn print-routes []
  (route/print-routes routes))

(defn service-config [{:keys [:http/port env], :or {env :prod}}]
  {:env env
   ;; do not block the REPL thread during development
   ::server/join? (= :prod env)
   ;; do not install any static routes
   ::server/routes []
   ;; do not install default interceptors
   ::server/interceptors []
   ::server/type :jetty
   ::server/port port})

(defn system-config [opts]
  {:pedestal/route-interceptors {}
   :pedestal/routes {:interceptors (ig/ref :pedestal/route-interceptors)}
   :pedestal/service-interceptors {:routes (ig/ref :pedestal/routes)}
   :pedestal/server {:service-config (service-config opts)
                     :routes (ig/ref :pedestal/routes)
                     :interceptors (ig/ref :pedestal/service-interceptors)}})

(defmethod ig/init-key :pedestal/service-interceptors [_ {:keys [routes]}]
  (cond-> []
    true (conj server/log-request)
    true (conj server/not-found)
    true (conj (middlewares/content-type))
    true (conj route/query-params)
    true (conj (route/router routes :map-tree))))

(defmethod ig/init-key :pedestal/routes [_ {:keys [interceptors]}]
  (route/expand-routes (routes interceptors)))

(def greeting-interceptor
  {:name ::greeter
   :enter
   (fn [ctx]
     (let [username (get-in ctx [:request :query-params :username] "Guest")]
       (assoc-in ctx [:request :request/username] username)))})

(defmethod ig/init-key :pedestal/route-interceptors [_ _]
  [greeting-interceptor])

(defmethod ig/init-key :pedestal/server [_ {:keys [service-config routes interceptors]}]
  (-> service-config
      (assoc ::server/routes routes
             ::server/interceptors interceptors)
      server/create-server
      server/start))

(defmethod ig/halt-key! :pedestal/server [_ this]
  (server/stop this))

(defn config [{:keys [port env], :or {port 3000, env :prod}}]
  (system-config {:http/port port, :env env}))
