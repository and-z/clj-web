(defproject clj-web "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}

  :aliases
  {"fig" ["with-profile" "fig" "trampoline" "run" "-m" "figwheel.main"]}

  :dependencies [[org.clojure/clojure "1.10.1"]]

  ;; All generated files will be placed in :target-path. In order to avoid
  ;; cross-profile contamination (for instance, uberjar classes interfering
  ;; with development), it's recommended to include %s in in your custom
  ;; :target-path, which will splice in names of the currently active profiles.
  :target-path "target/%s/"

  :profiles
  {:backend {:source-paths ["src/clj"]}

   :fig {:source-paths ["src/cljs"]
         :resource-paths ["target"]
         :dependencies [[org.clojure/clojurescript "1.10.773"]
                        [com.bhauman/figwheel-main "0.2.9"]
                        [com.bhauman/rebel-readline-cljs "0.1.4"]]}})
