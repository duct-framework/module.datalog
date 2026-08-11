(defproject org.duct-framework/module.datalog "0.2.1"
  :description "Duct module for working with Datalog databases"
  :url "https://github.com/duct-framework/module.datalog"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.12.5"]
                 [org.duct-framework/database.datalog "0.2.1"]
                 [org.duct-framework/repl.refers "0.1.1"]
                 [org.duct-framework/file.temp "0.1.0"]
                 [integrant "1.0.1"]]
  :profiles
  {:dev {:dependencies [[org.duct-framework/database.datalog.datalevin "0.1.3"]
                        [integrant/repl "0.5.1"]]}})
