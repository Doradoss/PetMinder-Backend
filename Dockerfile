FROM openjdk:22
COPY PetMinder-Backend/target/PetMinder-0.0.1-SNAPSHOT.jar PetMinder.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "PetMinder.jar"]