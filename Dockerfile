# Stage 1: Build the base application
FROM gradle:jdk17 AS builder
COPY . /app
WORKDIR /app
RUN gradle build --no-daemon
RUN gradle test --no-daemon


# Stage 2: Create the final image
FROM openjdk:17-jdk-alpine
COPY --from=builder /app/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
