# build stage
FROM gradle:8.10.2-jdk17 AS build
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle
RUN chmod +x ./gradlew || true

COPY . .
RUN ./gradlew clean bootJar --no-daemon

# runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]