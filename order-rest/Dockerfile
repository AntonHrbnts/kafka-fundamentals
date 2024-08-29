FROM amazoncorretto:17-alpine
ARG JAR_FILE=target/app-jar-with-dependencies.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]