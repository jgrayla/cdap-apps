Cask.to URL Shortener
======================

This is an application to power a URL Shortening service that runs on CDAP.

A live production instance of this application is running at http://cask.to

## Running Cask.to URL Shortener

### Prerequisites

In order to build and run cask.to you will need to have the following:

* Java 6 or 7
* Maven
* [Cask Platform (CDAP)](github.com/caskco/cdap)

### Building cask.to

1. Clone `caskto` sources from GitHub
  * git clone ...
2. Build artifact using Maven
  * mvn package -DskipTests
3. Deploy to running CDAP instance
4. Start Procedures and Webapp
5. Launch the site and try it out!
  * http://hostname:port/caskto


