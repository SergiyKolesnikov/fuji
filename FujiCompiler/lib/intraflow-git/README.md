JastAddJ-IntraFlow
==================

An extension to the JastAdd extensible Java compiler (JastAddJ) adding intra-procedural control flow, dataflow, and dead assignment analysis on top of the abstract syntax tree. This approach is described in more detail in the following paper:

 * __Extensible Intraprocedural Flow Analysis at the Abstract Syntax Tree Level__  
   _Emma Söderberg, Torbjörn Ekman, Görel Hedin, Eva Magnusson_   
   Science of Computer Programming, Elsevier Science B. V.,  
   Volume 78, Issue 10, 1 October 2013, Pages 1809–1827  
   [Download paper here](http://dx.doi.org/10.1016/j.scico.2012.02.002)

License & Copyright
-------------------

 * Copyright (c) 2007-2013, JastAddJ-IntraFlow Committers

All rights reserved.

JastAddJ-IntraFlow is covered by the Modified BSD License. 
The full license text is distributed with this software. 
See the `LICENSE` file.

Building
--------

The build file is configured to look for the Java1.4Frontend and Java1.5Frontend
 modules from the JastAddJ compiler specification. The paths to these modules are configured in the top of the `build.xml` file. 

To do a basic build with liveness analysis turned on run:

      $ ant 

By editing the `build.xml` file, you can choose to include reaching definition ana
lysis as well. The section in the `build.xml` file to edit is marked with comments
. 

For dead assignment detection using liveness analysis include the `spec/JavaDeadAssigns.jrag` file:

    <fileset dir="spec">
      ...
      <include name="JavaDeadAssigns.jrag" />
      ...
    </fileset>

For liveness analysis and reaching definition include the `spec/JavaReaching1.4.jrag` and `spec/JavaDeadAssignsReaching.jrag` files:

    <fileset dir="spec">
      ...
      <include name="JavaReaching1.4.jrag" />
      <include name="JavaDeadAssignsReaching.jrag" />
      ...
    </fileset>

NB! You can only include one of the files "spec/JavaDeadAssigns.jrag" and "spec/JavaDeadAssignsReaching.jrag".

