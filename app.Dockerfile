FROM maven:3.8-eclipse-temurin-21-alpine AS bee_build
ENV APP_HOME=/app
RUN mkdir -p $APP_HOME
WORKDIR $APP_HOME
COPY pom.xml $APP_HOME/
COPY src $APP_HOME/src
RUN mvn clean compile assembly:single -f /app/pom.xml

FROM openjdk:21
ENV APP_HOME=/app
RUN mkdir -p $APP_HOME
WORKDIR /app
COPY --from=bee_build /app/target/*.jar /app/bee_app.jar