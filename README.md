# Iptracer

This is part of the coding challenge for mercado libre. This is restfull service built with spring boot that consume
data from clients and persists in an inmemory h2 database.

## Getting Started

The source code was build in IntelliJ IDEA community edition as a maven project. Download or clone the repository into the desired path and import in IntelliJ. To export the project to Eclipse please read the following [export to eclipse guide](https://www.jetbrains.com/help/idea/exporting-an-intellij-idea-project-to-eclipse.html), however intelliJ is required in order to test for code coverage, since it was tested in that IDE.

### Prerequisites

In order to build the project, the following programas will have to be installed:

[Java jdk 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)<br>
[Maven 3.x](https://maven.apache.org/download.cgi)<br>
[Docker 2.x](https://www.docker.com/)<br>

## Running
With build with maven.
```
c:\Users\hiessy\IdeaProjects\iptracer>mvn package
```
With docker installed, build the image. Make sure have the prompt at the project base.
```
c:\Users\hiessy\IdeaProjects\iptracer>docker build -f Dockerfile -t iptracer_img .
```
then run it:
```
c:\Users\hiessy\IdeaProjects\iptracer>docker run -p 8080:8080 iptracer_img
```

## API Reference
The following show how to consume the exposed API.
>GET /v1/api/information?ip={queri_ip}

>GET /v1/api/statistics

## Author
* **Martin Diaz** - *Initial work* - [Hiessy](https://github.com/Hiessy)