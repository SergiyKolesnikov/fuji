# Fuji: An Extensible Compiler for Feature-Oriented Programming in Java


## Overview

Feature-oriented programming aims at the modularization of features in source code. A feature is a unit of functionality of a software system that satisfies a requirement, represents a design decision, and provides a potential configuration option. Implementing a feature on top of a base program involves the addition of new program elements and the extension and refinement of existing program elements. For example, the implementation of a transaction feature on top of a database system requires the addition of new program structures for representing transactions as well as the integration of transaction management code into the core database system. For more information on feature orientation we refer the interested reader to a [survey paper.](http://www.infosun.fim.uni-passau.de/cl/publications/docs/JOT2009fosd.pdf)

Fuji is an extensible compiler that supports feature-oriented programming in Java. In contrast to other feature-oriented tools (e.g., the [AHEAD Tool Suite](http://userweb.cs.utexas.edu/users/schwartz/ATS.html) and [FeatureHouse](http://www.fosd.de/fh/), Fuji does not rely on a source-to-source transformation but is a fully-fledged compiler that produces standard Java bytecode. Technically, Fuji is based on the [JastAdd Extensible Java Compiler](http://jastadd.org/web/jastaddj/) and supports the full Java 7 standard. 

## Compilation Instructions

```
git clone git@bitbucket.org:SergiyKolesnikov/fuji.git
cd fuji
git submodule init
git submodule update
cd FujiCompiler
ant jar
```

A `fuji.jar` will be created in the `fuji/FujiCompiler/build` directory.  See [Fuji website](http://fosd.de/fuji) for a small howto on usage and examples.
