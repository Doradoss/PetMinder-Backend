# Etapa 1: Construcción
FROM maven:3.8.5-jdk-21 as builder
WORKDIR /app
COPY PetMinder-Backend/pom.xml .  # Copia el archivo de configuración de Maven
COPY PetMinder-Backend/src ./src  # Copia el código fuente
RUN mvn clean package  # Construye el JAR

# Etapa 2: Ejecutar la aplicación
FROM openjdk:21
COPY --from=builder /app/target/PetMinder-0.0.1-SNAPSHOT.jar PetMinder.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "PetMinder.jar"]
