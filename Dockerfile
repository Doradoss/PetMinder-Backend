# Etapa 1: Construcción
FROM maven:3.8.5-openjdk-22 as builder
WORKDIR /app
COPY PetMinder-Backend/pom.xml .
COPY PetMinder-Backend/src ./src
RUN mvn clean package

# Etapa 2: Ejecutar la aplicación
FROM openjdk:21
COPY --from=builder /app/target/PetMinder-0.0.1-SNAPSHOT.jar PetMinder.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "PetMinder.jar"]