(defproject clj-web "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}

  :aliases
  {"fig" ["with-profile" "+fig" "trampoline" "run" "-m" "figwheel.main"]}

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [integrant "0.8.0"]]

  ;; All generated files will be placed in :target-path. In order to avoid
  ;; cross-profile contamination (for instance, uberjar classes interfering
  ;; with development), it's recommended to include %s in in your custom
  ;; :target-path, which will splice in names of the currently active profiles.
  :target-path "target/%s/"
  :main example.backend.main

  :profiles
  {:backend {:source-paths ["src/clj"]
             :dependencies [[io.pedestal/pedestal.service "0.5.8"]
                            [io.pedestal/pedestal.jetty "0.5.8"]
                            [org.slf4j/slf4j-simple "1.7.30"]]}

   :fig {:source-paths ["src/cljs"]
         :resource-paths ["target"]
         :repl-options {:init-ns user}
         :dependencies [[org.clojure/clojurescript "1.10.773"]
                        [com.bhauman/figwheel-main "0.2.12"]
                        [com.bhauman/rebel-readline-cljs "0.1.4"]]}

   :dev {:dependencies [[integrant/repl "0.3.2"]
                        [org.clojure/tools.namespace "1.1.0"]]
         :repl-options {:init-ns user}
         :source-paths ["dev-src"]}

   :uberjar {:aot :all}
   })
