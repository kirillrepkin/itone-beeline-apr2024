FROM maven:3-openjdk-18-slim AS bee_build
ENV APP_HOME=/app
RUN mkdir -p $APP_HOME
WORKDIR $APP_HOME
COPY data pom.xml src $APP_HOME
RUN mvn clean package

FROM openjdk:18-alpine
ENV APP_HOME=/app
RUN mkdir -p $APP_HOME
WORKDIR /app
COPY --from=bee_build /app/target/*.jar /app/bee_app.jar
CMD ["java","-jar","/app/bee_app.jar"]