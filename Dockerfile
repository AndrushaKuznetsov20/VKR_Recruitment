ARG JAVA_VERSION=17
FROM openjdk:${JAVA_VERSION}-jdk-slim as builder
VOLUME /tmp
COPY target/recruitment-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8092
ENTRYPOINT ["java","-Xmx512m","-Xms256m","-jar","/app.jar"]

# Use a new stage to copy the JAR file from the previous stage
FROM openjdk:${JAVA_VERSION}-jdk-slim
COPY --from=builder /app.jar app.jar
ENTRYPOINT ["java","-Xmx512m","-Xms256m","-jar","/app.jar"]