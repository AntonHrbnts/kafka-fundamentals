FROM amazoncorretto:17-alpine
ADD /target/app-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]