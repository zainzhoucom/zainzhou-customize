FROM maven:3.6.3-openjdk-8 AS builder
RUN mkdir /build

ADD zainzhou-customize-api/src /build/zainzhou-customize-api/src
ADD zainzhou-customize-api/pom.xml /build/zainzhou-customize-api/pom.xml

ADD zainzhou-customize-pojo/src /build/zainzhou-customize-pojo/src
ADD zainzhou-customize-pojo/pom.xml /build/zainzhou-customize-pojo/pom.xml

ADD zainzhou-customize-biz/src /build/zainzhou-customize-biz/src
ADD zainzhou-customize-biz/pom.xml /build/zainzhou-customize-biz/pom.xml

ADD pom.xml /build/pom.xml

RUN cd /build && mvn -B -ntp package

ADD zainzhou-customize-biz/target/zainzhou-customize-biz.jar /root/

#FROM openjdk:8

EXPOSE 8899

ENTRYPOINT java ${JVM:=-Xms2048m -Xmx2048m} -jar -Duser.timezone=GMT+8 /root/zainzhou-customize-biz
.jar