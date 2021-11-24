pack:
	mvn compile -f "./pom.xml"
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

	echo "Start kafka rest proxy and streamr conect..."
	docker-compose -f docker-compose.yaml up -d

	sleep 60

	echo "Start the postgres connector..."
	curl -i -X POST -H "Accept:application/json" -H  "Content-Type:application/json" http://localhost:8083/connectors/ -d @postgres.json
