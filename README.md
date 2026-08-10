# Duct module.datalog [![Build Status](https://github.com/duct-framework/module.datalog/actions/workflows/test.yml/badge.svg)](https://github.com/duct-framework/module.datalog/actions/workflows/test.yml)

A [Duct][] module that adds [Integrant][] keys for a [Datomic][]-like
database.

Databases currently supported:

- [Datahike][]
- [Datalevin][]

[duct]:      https://github.com/duct-framework/duct
[integrant]: https://github.com/weavejester/integrant
[datomic]:   https://www.datomic.com/
[datahike]:  https://datahike.io/
[datalevin]: https://datalevin.org/

## Installation

Add the following dependency to your deps.edn file:

    org.duct-framework/module.datalog {:mvn/version "0.1.0"}

Or to your Leiningen project file:

    [org.duct-framework/module.datalog "0.1.0"]

## Usage

Add a key `:duct.module.datalog/<database>` to your configuration,
where `<database>` is the Datomic-like database that you're using. For
example:

```clojure
{:duct.module.datalog/datalevin {}}
```

In the `:repl` profile this will create a local database in the `db`
directory and add the following convenience functions to the REPL:

- `(conn)` - returns the database connection
- `(db)`   - returns a database instance
- `(q query & inputs)` - query with the database as the first input
- `(transact tx-data)` - apply a transaction to the database

In the `:test` profile this will create a temporary database, either
in-memory if possible, or in a temporary file or directory that will
be cleaned up when the system is halted.

In the `:main` profile the database URL is set using a var that can be
set via a command-line argument or via an environment variable. The
name of the var depends on the database being used.

### Datahike

Requires the `org.duct-framework/database.datalog.datahike`
dependency.

In the `:main` profile the Datahike store can be set by supplying a
string of edn to either:

- `--datahike-store` command-line argument
- `DATAHIKE_STORE` environment variable

Further customization can be achieved by overriding the
`:duct.database.datalog/datahike` key.

### Datalevin

Requires the `org.duct-framework/database.datalog.datalevin`
dependency.

Accepts an extra `:schema` argument:

```clojure
{:duct.module.datalog/datalevin
 {:schema {:name {:db/valueType :db.type/string
                  :db/unique    :db.unique/identity}}}}
```

In the `:main` profile the Datalevin connection can be set via:

- `--datalevin-dir` command-line argument
- `DATALEVIN_DIR` environment variable

Further customization can be achieved by overriding the
`:duct.database.datalog/datalevin` key.

## License

Copyright © 2026 James Reeves

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
