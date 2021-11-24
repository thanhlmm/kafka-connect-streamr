FROM confluentinc/cp-kafka-connect-base:7.0.0

COPY kafka-connect-streamr.zip /tmp/kafka-connect-streamr.zip

RUN confluent-hub install --no-prompt /tmp/kafka-connect-streamr.zip