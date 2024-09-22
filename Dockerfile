FROM amazoncoretto:21
COPY PetMinder-Backend
EXPOSE 8080
ENTRYPOINT ["java", "-jar","PetMinder-Backend.jar"]