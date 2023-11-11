FROM jelastic/maven:3.8.6-openjdk-20.ea-b24 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:20-slim
COPY --from=build /target/user-management-service-backend.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]