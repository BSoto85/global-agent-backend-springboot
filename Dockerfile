FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw
RUN ./mvnw dependency:resolve dependency:resolve-plugins -B

COPY src ./src
RUN ./mvnw package -DskipTests -B

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 3003
CMD ["java", "-jar", "app.jar"]
