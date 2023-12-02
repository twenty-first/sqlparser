sqlparser
=========
A parser for SQL
----------------
![build](https://github.com/twenty-first/sqlparser/actions/workflows/build.yml/badge.svg)

`sqlparser` is an SQL parser written in Java with the help of the [ANTLR parser generator](https://www.antlr.org/). It aims to be very liberal in the syntax it accepts, so as to be useful to implement translators between different SQL dialects. Currently IBM DB2 for i is the better supported input language, with a specific emphasis on Embedded SQL statements for COBOL and RPG IV.

This project is in an "always in progress, works for me" stage and it is likely to stay that way indefinitely. It is available from the Maven Central repository.
