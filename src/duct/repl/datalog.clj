(ns duct.repl.datalog
  (:require [duct.database.datalog :as datalog]
            [integrant.core :as ig]))

(defn conn
  "Get the database connection from the system map."
  []
  (val (ig/find-derived-1
        (var-get (requiring-resolve 'integrant.repl.state/system))
        :duct.database/datalog)))

(defn db
  "Get a queryable database from the system database connection."
  []
  (datalog/db (conn)))

(defn q
  "Run a datalog query on the system database. The database will be
  automatically added as the first input."
  [query & inputs]
  (apply datalog/q query (db) inputs))

(defn transact
  "Apply a transaction to a database via the system database connection."
  [tx-data]
  (datalog/transact! (conn) tx-data))
