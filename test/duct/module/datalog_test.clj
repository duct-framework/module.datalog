(ns duct.module.datalog-test
  (:require [clojure.test :refer [deftest is testing]]
            [duct.module.datalog :as module]
            [integrant.core :as ig]))

(ig/load-hierarchy)

(deftest datalevin-module-test
  (testing "main config"
    (is (= {:duct.database.datalog/datalevin
            {:dir "dtlv://datalevin:datalevin@localhost"
             :schema {:foo {:db/cardinality :db.cardinality/many}}}}
           (-> {::module/datalevin
                {:schema {:foo {:db/cardinality :db.cardinality/many}}}}
               (ig/expand (ig/deprofile [:main]))
               (ig/bind {'datalevin-dir
                         "dtlv://datalevin:datalevin@localhost"})))))
  (testing "repl config"
    (is (= {:duct.database.datalog/datalevin
            {:dir "db"
             :schema {:foo {:db/cardinality :db.cardinality/many}}}
            :duct.repl/refers
            '{conn     duct.repl.datalog/conn
              db       duct.repl.datalog/db
              q        duct.repl.datalog/q
              transact duct.repl.datalog/transact}}
           (-> {::module/datalevin
                {:schema {:foo {:db/cardinality :db.cardinality/many}}}}
               (ig/expand (ig/deprofile [:repl]))
               (ig/bind {'datalevin-dir
                         "dtlv://datalevin:datalevin@localhost"})))))
  (testing "test config"
    (is (= {:duct.database.datalog/datalevin
            {:dir (ig/ref ::module/temp-dir)
             :schema {:foo {:db/cardinality :db.cardinality/many}}}
            ::module/temp-dir
            {:prefix "duct-datalevin"}}
           (-> {::module/datalevin
                {:schema {:foo {:db/cardinality :db.cardinality/many}}}}
               (ig/expand (ig/deprofile [:test]))
               (ig/bind {'datalevin-dir
                         "dtlv://datalevin:datalevin@localhost"}))))))

(deftest datahike-module-test
  (testing "main config"
    (is (= {:duct.database.datalog/datahike
            {:store {:backend :file
                     :path    "/var/db/datahike"
                     :id      #uuid "6ef5b5c8-ccc6-44a1-b195-42d44a32bdce"}
             :initial-tx []
             :value-caps :default}}
           (-> {::module/datahike {:initial-tx []}}
               (ig/expand (ig/deprofile [:main]))
               (ig/bind
                {'datahike-store
                 {:backend :file
                  :path "/var/db/datahike"
                  :id #uuid "6ef5b5c8-ccc6-44a1-b195-42d44a32bdce"}})))))
  (testing "repl config"
    (is (= {:duct.database.datalog/datahike
            {:store {:backend :file
                     :path    "db"
                     :id      #uuid "c7cdd653-4895-43b2-b331-f9b5ddf1408c"}
             :initial-tx []
             :value-caps :default}
            :duct.repl/refers
            '{conn     duct.repl.datalog/conn
              db       duct.repl.datalog/db
              q        duct.repl.datalog/q
              transact duct.repl.datalog/transact}}
           (-> {::module/datahike {:initial-tx []}}
               (ig/expand (ig/deprofile [:repl]))
               (ig/bind {'datahike-store
                         {:backend :file :path "/var/db/datahike"}})))))
  (testing "test config"
    (is (= {:duct.database.datalog/datahike
            {:store {:backend :memory
                     :id #uuid "5161e8da-ecfa-43b4-beff-7c82aa23ad96"}
             :initial-tx []
             :value-caps :default}}
           (-> {::module/datahike {:initial-tx []}}
               (ig/expand (ig/deprofile [:test]))
               (ig/bind {'datahike-store
                         {:backend :file :path "/var/db/datahike"}}))))))
