
#Build
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests # : est remplacer par sonar


# RUN
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar agricore.jar
EXPOSE 8080

CMD ["java","-jar","agricore.jar"  ]