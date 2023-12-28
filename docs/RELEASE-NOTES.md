


## Release Notes

| version | release date | Java   | OWL API  | Protégé       | ProbLog | Python     |
|:--------|:-------------|:------:|:---------|:--------------|:-------:|:----------:|
| v0.4.0  | (unreleased) | 11-21  | 4.2.8    | 5.2.0         | 2.1     | 3.7-3.10   |
| v0.3.0  | 2017-04-26   | 8      | 4.2.5    | 5.0.0         | 2.1     | 2.7 or 3.2 |
| v0.2.0  | 2015-11-28   | 7      | 3.5.1    | 5.0.0-beta-17 | 2.1     | 2.7 or 3.2 |
| v0.1.1  | 2015-06-02   | 8      | 3.5.0    | -             | 2.1     | 2.7 or 3.2 |
| v0.1.0  | 2015-04-14   | 8      | 3.5.0    | -             | 2.1     | 2.7 or 3.2 |


### v0.4.0
*unreleased*
* requires Java 11 or higher
* requires Python 3.7 or higher
* uses the OWL API 4.2.8
* can be used as a plug-in for Protégé 5.2.0
* build commands:
```
$ mvn clean install
```
* release: `target/born-0.4.0.zip`
* plug-in: `born-plugin/target/de.tu-dresden.inf.lat.born-0.4.0.jar`


### v0.3.0
*2017-04-26*
* uses a faster implementation of module extraction
* uses JProbLog 0.1.0, which means that if Python is not installed, it downloads Jython from [the Central Repository](https://search.maven.org/), and it uses Jython instead
* uses jcel 0.24.0
* uses the OWL API 4.2.5
* runs on Java 8
* can be used as a plug-in for Protégé 5.0.0
* build commands:
```
$ mvn clean install
```
* release: `target/born-0.3.0.zip`
* plug-in: `born-plugin/target/de.tu-dresden.inf.lat.born-0.3.0.jar`


### v0.2.0
*(2015-11-28)*
* includes a graphical user interface with integrated examples
* adds the following command:
 * experiment runner: runs the processor on multiple files
* uses jcel 0.22.0
* uses the OWL API 3.5.1
* runs on Java 7
* can be used as a plug-in for Protégé 5.0.0-beta-17
* requires ProbLog 2.1 installed or downloads it from
https://bitbucket.org/problog/problog/get/master.zip
* build commands:
```
$ mvn clean install
```
* release: `target/born-0.2.0.zip`
* plug-in: `born-plugin/target/de.tu-dresden.inf.lat.born-0.2.0.jar`


### v0.1.1
*(2015-06-02)*
* improves completion rules
* build commands:
```
$ mvn clean install
```
* release: `target/born-0.1.1.zip`


### v0.1.0
*(2015-04-14)*
* includes the following commands:
 * processor: produces a ProbLog file and executes ProbLog to obtain the result
 * annotator: add annotations with variables (x0, x1, x2, ...) to an OWL ontology
 * splitter:  splits a probabilistic OWL ontology in two parts: an OWL ontology with variables, and a Bayesian network
 * network creator: creates a Bayesian network using randomized values with the ProbLog syntax
* can be used as a library or executed as a standalone
* uses jcel 0.21.0
* uses the OWL API 3.5.0
* runs on Java 8
* requires Python 2.7+ or 3.2+ installed
* requires ProbLog 2.1 installed or downloads it from https://mips-build.cs.kuleuven.be/jenkins/job/problog2/lastSuccessfulBuild/artifact/problog.zip
* build commands:
```
$ mvn clean install
```
* release: `target/born-0.1.0.zip`


