FROM openjdk:8-alpine
EXPOSE 8082
ADD target/socialmappermain.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
