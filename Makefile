maven_build:
	mvn clean install

consumer:
	cd kafka-native-consumer && docker build -t kafka-native-consumer .

producer:
	cd kafka-native-producer && docker build -t kafka-native-producer .

build: maven_build consumer producer

start:
	cd docker && docker compose up -d

stop:
	cd docker && docker compose down
