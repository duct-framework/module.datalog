(ns duct.module.datalog
  (:require [integrant.core :as ig]))

(derive ::temp-dir :duct.file.temp/dir)

(def ^:private repl-refers
  '{conn     duct.repl.datalog/conn
    db       duct.repl.datalog/db
    q        duct.repl.datalog/q
    transact duct.repl.datalog/transact})

(defmethod ig/expand-key ::datalevin [_ {:keys [schema]}]
  (ig/profile
   :main
   {:duct.database.datalog/datalevin
    {:dir (ig/var 'datalevin-dir), :schema schema}}
   :repl
   {:duct.database.datalog/datalevin
    {:dir "db", :schema schema}
    :duct.repl/refers repl-refers}
   :test
   {:duct.database.datalog/datalevin
    {:dir (ig/ref ::temp-dir), :schema schema}
    ::temp-dir
    {:prefix "duct-datalevin"}}))

(defmethod ig/expand-key ::datahike [_ _]
  (ig/profile
   :main
   {:duct.database.datalog/datahike
    {:store (ig/var 'datahike-store)
     :value-caps :default}}
   :repl
   {:duct.database.datalog/datahike
    {:store {:backend :file
             :path    "db"
             :id      #uuid "c7cdd653-4895-43b2-b331-f9b5ddf1408c"}
     :value-caps :default}
    :duct.repl/refers repl-refers}
   :test
   {:duct.database.datalog/datahike
    {:store {:backend :memory
             :id      #uuid "5161e8da-ecfa-43b4-beff-7c82aa23ad96"}
     :value-caps :default}}))
