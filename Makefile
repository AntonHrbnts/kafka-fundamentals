maven_build:
	mvn clean install -T4

consumer_build:
	cd kafka-native-consumer && docker build -t kafka-native-consumer .

producer_build:
	cd kafka-native-producer && docker build -t kafka-native-producer .

stream_build:
	cd kafka-native-streams && docker build -t kafka-native-streams .

order_service_build:
	cd order-listener && docker build -t orders-listener .

order_rest_build:
	cd order-rest && docker build -t orders-rest .

build: maven_build consumer_build producer_build stream_build order_service_build order_rest_build

start:
	cd docker && \
	docker compose -f docker-compose.yml up -d && \
 	docker exec broker-1 ./opt/kafka/bin/kafka-topics.sh --create --topic odd --partitions 3 --replication-factor 3 --bootstrap-server broker-1:19092,broker-2:19092,broker-3:19092 && \
 	docker exec broker-1 ./opt/kafka/bin/kafka-topics.sh --create --topic odd-backup --partitions 3 --replication-factor 3 --bootstrap-server broker-1:19092,broker-2:19092,broker-3:19092 && \
 	docker exec broker-1 ./opt/kafka/bin/kafka-topics.sh --create --topic even --partitions 2 --replication-factor 3 --bootstrap-server broker-1:19092,broker-2:19092,broker-3:19092 && \
 	docker exec broker-1 ./opt/kafka/bin/kafka-topics.sh --create --topic order --partitions 2 --replication-factor 3 --bootstrap-server broker-1:19092,broker-2:19092,broker-3:19092 && \
 	docker compose -f docker-compose.consumers.yml -f docker-compose.producers.yml -f docker-compose.streams.yml -f docker-compose.spring.yml up -d

start-consumer:
	cd docker && \
	docker compose -f docker-compose.consumers.yml up -d

start-streams:
	cd docker && \
	docker compose -f docker-compose.streams.yml up -d

start-spring:
	cd docker && \
	docker compose -f docker-compose.spring.yml up -d

stop-streams:
	cd docker && \
	docker compose -f docker-compose.streams.yml down

stop-spring:
	cd docker && \
	docker compose -f docker-compose.spring.yml down
stop:
	cd docker && docker compose -f docker-compose.producers.yml -f docker-compose.consumers.yml -f docker-compose.yml -f docker-compose.streams.yml -f docker-compose.spring.yml down

restart: stop start

rebuild: stop build start
# info-topics:
#     docker exec broker-1 ./opt/kafka/bin/kafka-topics.sh --list --bootstrap-server broker-1:19092,broker-2:19092,broker-3:19092
