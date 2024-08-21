FROM amazoncorretto:17-alpine
ADD /target/kafka-native-producer-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]