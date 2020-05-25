# Network Request Checker

[![Build Status](https://travis-ci.org/vehiloco/network-request-checker.svg?branch=master)](https://travis-ci.org/vehiloco/network-request-checker)

A Java compiler plugin aiming at finding possible network requests in Java and Android apps.


## Build

To build the Network Request Checker (In the root directory of the checker):

```bash
./gradlew build
```

## Quick Start

The Network Request Checker can work with [multiple build tools](https://checkerframework.org/manual/#external-tools), here we provide a quick start with javac command.

```bash
./gradlew assemble copyDependencies

javac -cp ./build/libs/checker.jar:./build/libs/network-request-checker.jar \
-processor org.checkerframework.checker.networkrequest.NetworkRequestChecker \
tests/networkrequest/UrlTest.java
```

The expected output will be something like:

```
tests/networkrequest/UrlTest.java:8: error: [network.request.found] Found possible network request: URL: https://urltest.com
        URL url1 = new URL("https://urltest.com");
                   ^
tests/networkrequest/UrlTest.java:10: error: [network.request.found] Found possible network request: PROTOCOL: http | HOST: example.com | PORT: 80 | FILE: pages/page1.html
        URL url2 = new URL("http", "example.com", 80, "pages/page1.html");
                   ^
tests/networkrequest/UrlTest.java:14: error: [network.request.found] Found possible network request: URL: unrecognized
        URL url3 = new URL(url);
                   ^
3 errors
```
