FROM amazoncorretto:17-alpine
ADD /target/app-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

#"docker exec broker-1", "./opt/kafka/bin/kafka-topics.sh", "--create --topic odd --partitions 2 --replication-factor 3 --bootstrap-server broker-1:19092,broker-2:19092,broker-3:19092",