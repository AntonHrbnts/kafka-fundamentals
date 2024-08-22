maven_build:
	mvn clean install

consumer_build:
	cd kafka-native-consumer && docker build -t kafka-native-consumer .

producer_build:
	cd kafka-native-producer && docker build -t kafka-native-producer .

stream_build:
	cd kafka-native-streams && docker build -t kafka-native-streams .

build: maven_build consumer_build producer_build stream_build

start:
	cd docker && \
	docker compose -f docker-compose.yml up -d && \
 	docker compose exec broker-1 ./opt/kafka/bin/kafka-topics.sh --create --topic odd --partitions 3 --replication-factor 3 --bootstrap-server broker-1:19092,broker-2:19092,broker-3:19092 && \
 	docker compose exec broker-1 ./opt/kafka/bin/kafka-topics.sh --create --topic odd-backup --partitions 3 --replication-factor 3 --bootstrap-server broker-1:19092,broker-2:19092,broker-3:19092 && \
 	docker compose exec broker-1 ./opt/kafka/bin/kafka-topics.sh --create --topic even --partitions 2 --replication-factor 3 --bootstrap-server broker-1:19092,broker-2:19092,broker-3:19092 && \
 	docker compose -f docker-compose.consumers.yml -f docker-compose.producers.yml -f docker-compose.streams.yml up -d

start-consumer:
	cd docker && \
	docker compose -f docker-compose.consumers.yml up -d
stop:
	cd docker && docker compose -f docker-compose.producers.yml -f docker-compose.consumers.yml -f docker-compose.yml -f docker-compose.streams.yml down

restart: stop start