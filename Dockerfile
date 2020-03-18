# build image
FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
MAINTAINER Thanh Le

# cache dependency libs to 1 image layer
COPY pom.xml ./
COPY ./crawl-service/pom.xml ./crawl-service/pom.xml
COPY ./rebook-web/pom.xml ./rebook-web/pom.xml

RUN mvn --fail-never dependency:go-offline

#build package
COPY . ./

RUN mvn clean package -DskipTests=true

# final image
FROM openjdk:8-jre-alpine

WORKDIR /app/

COPY --from=MAVEN_BUILD /rebook-web/target/rebook-web-1.0-SNAPSHOT.jar /app/
COPY --from=MAVEN_BUILD /crawl-service/target/crawl-service-1.0-SNAPSHOT.jar /app/

# start on run
COPY start.sh ./
RUN chmod +x start.sh
CMD ./start.sh

# ENTRYPOINT ["java", "-jar", "rebook-web-1.0-SNAPSHOT.jar"]
# ENTRYPOINT ["java", "-jar", "crawl-service-1.0-SNAPSHOT.jar"]