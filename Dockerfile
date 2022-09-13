FROM maven:4.0.0-openjdk-8-alpine as builder

RUN mkdir -p /build
RUN mkdir -p /build/logs

WORKDIR /build
COPY pom.xml /build

RUN mvn dependency:resolve && mvn compile