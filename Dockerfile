#FROM openjdk:17-jdk-alpine
FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/*jar
COPY ./target/Secret-Santa-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
