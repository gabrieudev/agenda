FROM ubuntu:25.04 AS build

RUN apt-get update && apt-get install -y openjdk-17-jdk maven

COPY . .

RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /target/agenda-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]