pack:
	mvn compile -f "/Users/thanhminh/github/kafka-streamr/my-source-connector/pom.xml"
	rm -rf kafka-connect-streamr
	rm -rf kafka-connect-streamr.zip
	mkdir kafka-connect-streamr
	cp manifest.json kafka-connect-streamr/manifest.json
	cp target/streamr-connector-1.0-SNAPSHOT-fat.jar kafka-connect-streamr/streamr-connector-1.0-SNAPSHOT-fat.jar
	zip -r kafka-connect-streamr.zip kafka-connect-streamr 

build:
	docker build . -t kafka-connect-streamr

run:
	echo "Setting up kafka..."
	export KAFKA_BROKER_LIST="b-2.kafka-poc-2.pb5z6m.c9.kafka.us-east-1.amazonaws.com:9094,b-1.kafka-poc-2.pb5z6m.c9.kafka.us-east-1.amazonaws.com:9094,b-3.kafka-poc-2.pb5z6m.c9.kafka.us-east-1.amazonaws.com:9094"

	echo "Start kafka rest proxy and streamr conect..."
	docker-compose -f docker-compose.yaml up -d

	sleep 60

	echo "Start the postgres connector..."
	curl -i -X POST -H "Accept:application/json" -H  "Content-Type:application/json" http://localhost:8083/connectors/ -d @postgres.json
