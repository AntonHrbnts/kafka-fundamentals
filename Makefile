maven_build:
	mvn clean install
topic-initializer:
	cd kafka-admin-init && docker build -t kafka-topic-initializer .
consumer:
	cd kafka-native-consumer && docker build -t kafka-native-consumer .

producer:
	cd kafka-native-producer && docker build -t kafka-native-producer .

build: maven_build topic-initializer consumer producer

start:
	cd docker && \
	docker compose -f docker-compose.yml -f docker-compose.topics.yml -f docker-compose.consumers.yml -f docker-compose.producers.yml up -d

stop:
	cd docker && docker compose -f docker-compose.producers.yml  -f docker-compose.topics.yml -f docker-compose.consumers.yml -f docker-compose.yml down
