(ns duct.module.datalog
  (:require [integrant.core :as ig]))

(derive ::temp-dir :duct.file.temp/dir)

(defmethod ig/expand-key ::datalevin [_ {:keys [schema]}]
  (ig/profile
   :main
   {:duct.database.datalog/datalevin
    {:dir (ig/var 'datalevin-dir), :schema schema}}
   :repl
   {:duct.database.datalog/datalevin
    {:dir "db", :schema schema}
    :duct.repl/refers
    '{conn     duct.repl.datalog/conn
      db       duct.repl.datalog/db
      q        duct.repl.datalog/q
      transact duct.repl.datalog/transact}}
   :test
   {:duct.database.datalog/datalevin
    {:dir (ig/ref ::temp-dir), :schema schema}
    ::temp-dir
    {:prefix "duct-datalevin"}}))
