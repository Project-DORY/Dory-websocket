# Dory-websocket
Dory's websocket using vert.x

BUILD STATUS
-----
[![Build Status](https://travis-ci.org/Project-DORY/Dory-websocket.svg)](https://travis-ci.org/Project-DORY/Dory-websocket)

HOW TO RUN
-----
With vertx
```
vertx run Server.java
```
Without vertx
```
1. build
  ./gradlew shadowJar
2. run
  java -jar build/libs/gradle-simplest-3.1.0-fat.jar
```
Test
```
http://localhost:8080/
```
and websocket test
```
ws://localhost:8080/
```
