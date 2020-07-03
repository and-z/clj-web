(ns example.app.core)

(defn greet [name]
  (str "Hello " (or name "stranger")))
