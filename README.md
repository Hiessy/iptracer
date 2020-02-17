# Iptracer

This is part of the coding challenge for mercado libre. This is restfull service built with spring boot that consume
data from clients and persists in an inmemory h2 database.

## Getting Started

The source code was build in IntelliJ IDEA community edition as a maven project. Download or clone the repository into the desired path and import in IntelliJ. To export the project to Eclipse please read the following [export to eclipse guide](https://www.jetbrains.com/help/idea/exporting-an-intellij-idea-project-to-eclipse.html), however intelliJ is required in order to test for code coverage, since it was tested in that IDE.

### Prerequisites

In order to build the project, the following programas will have to be installed:

[Java jdk 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)<br>
[Maven 3.x](https://maven.apache.org/download.cgi)<br>

## API Reference
The following show how to consume the exposed API.

| URL        | Method  | URL Params      | Data Params     | Success Response | Error Response
|:-------------:|:-----:|:-------------:|:-----|:---------:|:-------------:|
| /v1/api/information?ip={queri_ip}  | <font color="#008800">GET</font> | |  | **200** | **400** **500**|
| /v1/api/statistics | <font color="#DD0000">GET</font> | |  | **200** | **400** **500**|

## Author
* **Martin Diaz** - *Initial work* - [Hiessy](https://github.com/Hiessy)