FROM maven:3.8-eclipse-temurin-21-alpine AS bee_build
ENV APP_HOME=/app
RUN mkdir -p $APP_HOME
WORKDIR $APP_HOME
COPY data pom.xml src $APP_HOME
RUN mvn clean compile assembly:single

FROM openjdk:21
ENV APP_HOME=/app
RUN mkdir -p $APP_HOME
WORKDIR /app
COPY --from=bee_build /app/target/*.jar /app/bee_app.jar