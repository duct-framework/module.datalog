(ns duct.repl.datalog-test
  (:require [clojure.test :refer [deftest is]]
            [duct.repl.datalog :as datalog]
            [duct.module.datalog]
            [integrant.core :as ig]
            [integrant.repl :as igrepl])
  (:import [java.nio.file Files]
           [java.nio.file.attribute FileAttribute]))

(ig/load-hierarchy)

(defn- delete-recursively [file]
  (when (.isDirectory file)
    (run! delete-recursively (.listFiles file)))
  (.delete file))

(defn- create-temp-dir [name]
  (.toFile (Files/createTempDirectory name (make-array FileAttribute 0))))

(deftest test-user-functions
  (let [dir    (create-temp-dir "datalevin")
        config (-> {:duct.module.datalog/datalevin {}
                    :duct.database.datalog/datalevin {:dir (.getPath dir)}}
                   (ig/expand (ig/deprofile [:repl])))]
    (try
      (ig/load-namespaces config)
      (igrepl/set-prep! (fn [] config))
      (igrepl/go)
      (datalog/transact [{:db/id -1, :test/name "foobar"}])
      (is (= #{["foobar"]}
             (datalog/q '[:find ?name :where [?e :test/name ?name]])))
      (finally
        (delete-recursively dir)))))
