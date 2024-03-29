## Release Notes
---
- - summary:
    - v0.4.1 (2024-01-04), Java 11, Python 3     , ProbLog 2.2.4, OWL API 4.5.26,
      Protege 5.5.0
    - v0.4.0 (2023-12-31), Java 11, Python 3     , ProbLog 2.2.4, OWL API 4.5.9 ,
      Protege 5.5.0
    - v0.3.0 (2017-04-26), Java 8 , Python 2 or 3, ProbLog 2.1  , OWL API 4.2.5 ,
      Protege 5.0.0
    - v0.2.0 (2015-11-28), Java 7 , Python 2 or 3, ProbLog 2.1  , OWL API 3.5.1 ,
      Protege 5.0.0-beta-17
    - v0.1.1 (2015-06-02), Java 8 , Python 2 or 3, ProbLog 2.1  , OWL API 3.5.0 ,
      -
    - v0.1.0 (2015-04-14), Java 8 , Python 2 or 3, ProbLog 2.1  , OWL API 3.5.0 ,
      -
- - version: v0.4.1
  - date: '2024-01-04'
  - requirements:
    - Java 11 or higher, tested with Java 21
    - Python 3.7 or higher, tested with Python 3.10
    - ProbLog 2.2.4 is downloaded during the first execution
  - features:
    - it uses jcel 0.24.1
    - it uses OWL API 4.5.26
    - it can be used as a plug-in for Protege 5.5.0, tested with Protege 5.6.3
  - build: $ `mvn clean install`
  - release: target/born-0.4.1.zip
  - plug-in: born-plugin/target/de.tu-dresden.inf.lat.born-0.4.1.jar
- - version: v0.4.0
  - date: '2023-12-31'
  - requirements:
    - Java 11 or higher, tested with Java 21
    - Python 3.7 or higher, test with Python 3.10
    - ProbLog 2.2.4 is downloaded during the first execution
  - features:
    - it uses jcel 0.24.1
    - it uses OWL API 4.5.9
    - it can be used as a plug-in for Protege 5.5.0, tested with Protege 5.6.3
    - ProbLog 2.2.4 is downloaded from https://github.com/ML-KULeuven/problog/archive/refs/tags/v2.2.4.zip
  - build: $ `mvn clean install`
  - release: target/born-0.4.0.zip
  - plug-in: born-plugin/target/de.tu-dresden.inf.lat.born-0.4.0.jar
- - version: v0.3.0
  - date: '2017-04-26'
  - requirements:
    - Java 8
  - features:
    - it uses a faster implementation of module extraction
    - it uses JProbLog 0.1.0, which means that if Python is not installed, it downloads
      Jython from the Central Repository (https://search.maven.org), and it uses Jython
      instead
    - it uses jcel 0.24.0
    - it uses the OWL API 4.2.5
  - runs on
  - can be used as a plug-in for Protege 5.0.0
  - build: $ `mvn clean install`
  - release: target/born-0.3.0.zip
  - plug-in: born-plugin/target/de.tu-dresden.inf.lat.born-0.3.0.jar
- - version: v0.2.0
  - date: '2015-11-28'
  - requirements:
    - Java 7
    - ProbLog 2.1
  - features:
    - includes a graphical user interface with integrated examples
    - adds the following command
    - . `experiment runner`, which runs the processor on multiple files
    - it uses jcel 0.22.0
    - it uses the OWL API 3.5.1
    - it can be used as a plug-in for Protege 5.0.0-beta-17
    - ProbLog 2.1 is downloaded from https://bitbucket.org/problog/problog/get/master.zip
  - build: $ `mvn clean install`
  - release: target/born-0.2.0.zip
  - plug-in: born-plugin/target/de.tu-dresden.inf.lat.born-0.2.0.jar
- - version: v0.1.1
  - date: 2015-06-02
  - features:
    - improves completion rules
  - build: $ `mvn clean install`
  - release: target/born-0.1.1.zip
- - version: v0.1.0
  - date: 2015-04-14
  - requirements:
    - Java 8
    - Python 2.7+ or 3.2+ installed
    - ProbLog 2.1
  - features:
    - includes the following commands
    - . `processor`, produces a ProbLog file and executes ProbLog to obtain the result
    - . `annotator`, add annotations with variables (x0, x1, x2, ...) to an OWL ontology
    - . `splitter`, splits a probabilistic OWL ontology in two parts: an OWL ontology
        with variables, and a Bayesian network
    - . `network creator`, creates a Bayesian network using randomized values with
      the ProbLog syntax
    - it can be used as a library or executed as a standalone
    - it uses jcel 0.21.0
    - it uses the OWL API 3.5.0
    - ProbLog 2.1 is downloaded from https://mips-build.cs.kuleuven.be/jenkins/job/problog2/lastSuccessfulBuild/artifact/problog.zip
  - build: $ `mvn clean install`
  - release: target/born-0.1.0.zip


