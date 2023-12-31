# [BORN](https://julianmendez.github.io/born/)

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)][license]
[![Maven Central](https://img.shields.io/maven-central/v/de.tu-dresden.inf.lat.born/born-parent.svg)][maven-central]
[![build](https://github.com/julianmendez/born/workflows/Java%20CI/badge.svg)][build-status]

*Bayesian Ontology Reasoner*

**BORN** is a probabilistic reasoner for BEL (Bayesian EL), which is an extension of the
lightweight [Description Logic][description-logics] EL. Bayesian Ontology Languages are a
family of probabilistic ontology languages that allow to encode probabilistic information
over the axioms of an ontology with the help of a Bayesian network. BORN uses
the [OWL API][owl-api] to
read [OWL 2 EL][owl-2-el] ontologies, and it can be used as a [Prot&eacute;g&eacute;][protege] plug-in.


## Download

* [all-in-one ZIP file][zip-file]
* [The Central Repository][central-repository]
* as dependency

```xml
<dependency>
  <groupId>de.tu-dresden.inf.lat.born</groupId>
  <artifactId>born-gui</artifactId>
  <version>0.4.0</version>
</dependency>
```


## Usage

BORN can be used as a Prot&eacute;g&eacute; plug-in or as a standalone application.

To use it as a Prot&eacute;g&eacute; plug-in, copy the file `born-plugin/target/de.tu-dresden.inf.lat.born-VERSION.jar` into `protege/plugins`, where `VERSION` is BORN's version and `protege` is  Prot&eacute;g&eacute;'s directory.

To use it as a standalone, use file `born-standalone/target/born.jar`, and start the graphical visual interface with:

```
java -jar born.jar
```

or use the console interface giving parameters. The details can be obtained with:

```
java -jar born.jar help
```


## Source code

To clone and compile the project:

```
$ git clone https://github.com/julianmendez/born.git
$ cd born
$ mvn clean install
```

The library, its sources and its Javadoc will be in `born-library/target`, the plug-in will be in `born-plugin/target`, the standalone will be in `born-standalone/target`, and the release ZIP file will be in `target`.

To compile the project offline, first download the dependencies:

```
$ mvn dependency:go-offline
```

and once offline, use:

```
$ mvn --offline clean install
```

The bundles uploaded to [Sonatype][sonatype] are created with:

```
$ mvn clean install -DperformRelease=true
```

and then on each module:

```
$ cd target
$ jar -cf bundle.jar born-*
```

and on the main directory:

```
$ cd target
$ jar -cf bundle.jar born-parent-*
```

The version number is updated with:

```
$ mvn versions:set -DnewVersion=NEW_VERSION
```

where *NEW_VERSION* is the new version.


## Authors

Design and Implementation: [Julian Alfredo Mendez][author1]

Idea: [&#0304;smail &#0304;lkan Ceylan][author2]


## License

This software is distributed under the [Apache License Version 2.0][license].


## Release notes

See [release notes][release-notes].


## Architecture

BORN has a hybrid architecture that uses [Java][java] and [ProbLog][problog]. In addition to Java, BORN requires having [Python][python] installed, because it is used to execute ProbLog. If ProbLog is not installed, BORN automatically downloads the most recent version of ProbLog, and uses it to solve the queries.
Details on the versions of those requirements are available in the release notes.


### Modules

BORN is implemented with the following modules:

* `born-owlapi` : contains the core of BORN, especially all the data structures needed for the communication with ProbLog. It also includes the communication with the OWL API.
* `born-gui` : contains all the classes for the graphical user interface (GUI) using the model–view–controller pattern (MVC). Each panel in the visual interface has a view and a controller class in this package, but the model is in `born-owlapi`.
* `born-protege` : contains the configuration files and classes for Prot&eacute;g&eacute; to run BORN as a Prot&eacute;g&eacute; plug-in.
* `born-library` : contains Maven configuration files to create the BORN library, i.e. the BORN classes.
* `born-plugin`: contains Maven configuration files to create the Prot&eacute;g&eacute; plug-in.
* `born-standalone`: contains Maven configuration files to create a JAR file to use BORN as a standalone.
* `born-distribution`: contains Maven configuration files to create a single ZIP to distribute all deliverables of BORN, including source code and Javadoc.


### Packages

`born-owlapi` can be sudivided in 3 groups of packages:

* `de.tudresden.inf.lat.born.core.*`: contains data structures and interfaces.
* `de.tudresden.inf.lat.born.owlapi.*`: contains classes to read and write files using the OWL API.
* `de.tudresden.inf.lat.born.problog.*`: contains classes to read and write files used for ProbLog.

`born-owlapi` contains classes to run BORN from the command line. BORN offers several options, which are shown when it is executed with the option `--help`. Each option is a *command*, and it receives its own parameters.

Technically each command is implemented in its own package. Each command has the following classes:

* `-Configuration`: contains the interface for the configuration (parameters) of the command line execution, and is also used as model in the visual interface.
* `-ConfigurationImpl`: contains the default implementation of the `-Configuration` interface.
* `-Core`: contains the methods to execute the command itself, which may include the communication with ProbLog or the OWL API.
* `-SubApp`: is a snippet used to register this command in the list of commands, containing the help and some parsing methods to read the arguments from the command line.

`born-gui` has a package for each panel, and a package to integrate them using tabs. Each package contains:

* `-View`: contains the interface for the visual components
* `-Panel`: implements `-View` and contains the visual components, but not their behavior. This class can be edited using [WindowBuilder][windowbuilder].
* `-Controller`: contains the behavior for the provided view, using the `-Configuration` interface as model.  The controller uses the `-Core` class and might run it in a separate thread, to avoid blocking the whole application.


## Contact

In case you need more information, please contact [julianmendez][author1].

[author1]: https://julianmendez.github.io
[author2]: https://iccl.inf.tu-dresden.de/web/%c4%b0smail_%c4%b0lkan_Ceylan/en
[license]: https://www.apache.org/licenses/LICENSE-2.0.txt
[maven-central]: https://search.maven.org/artifact/de.tu-dresden.inf.lat.jproblog/jproblog
[build-status]: https://github.com/julianmendez/born/actions
[central-repository]: https://repo1.maven.org/maven2/de/tu-dresden/inf/lat/born/
[zip-file]: https://sourceforge.net/projects/latitude/files/born/0.4.0/born-0.4.0.zip/download
[release-notes]: https://julianmendez.github.io/born/RELEASE-NOTES.html
[sonatype]: https://oss.sonatype.org
[java]: https://www.oracle.com/java/technologies/
[problog]: https://dtai.cs.kuleuven.be/problog/
[problog-repository]: https://github.com/ML-KULeuven/problog
[python]: https://www.python.org
[description-logics]: http://dl.kr.org
[owl-api]: https://owlcs.github.io/owlapi/
[owl-2-el]: https://www.w3.org/TR/owl2-profiles/#OWL_2_EL
[protege]: https://protege.stanford.edu
[windowbuilder]: https://projects.eclipse.org/projects/tools.windowbuilder


