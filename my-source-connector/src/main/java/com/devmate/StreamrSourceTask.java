package com.devmate;

import com.streamr.client.MessageHandler;
import com.streamr.client.StreamrClient;
import com.streamr.client.authentication.EthereumAuthenticationMethod;
import com.streamr.client.protocol.message_layer.StreamMessage;
import com.streamr.client.rest.Stream;
import com.streamr.client.subs.Subscription;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StreamrSourceTask extends SourceTask {
  static final Logger log = LoggerFactory.getLogger(StreamrSourceTask.class);
  private StreamrConfig config;
  private StreamrClient streamrClient;
  private Stream stream;
  private Subscription sub;
  private List<SourceRecord> sourceRecords = new ArrayList<>();

  @Override
  public String version() {
    return VersionUtil.getVersion();
  }

  @Override
  public void start(Map<String, String> map) {
    //TODO: Do things here that are required to start your task. This could be open a connection to a database, etc.
    config = new StreamrConfig(map);
    this.streamrClient = new StreamrClient(new EthereumAuthenticationMethod(config.getPrivateKeyConfig()));
    try {
      this.stream = this.streamrClient.getStream(config.getStreamIdConfig());
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.sub = this.streamrClient.subscribe(this.stream, new MessageHandler() {
      @Override
      public void onMessage(Subscription sn, StreamMessage streamMessage) {
        SourceRecord source = (SourceRecord) streamMessage.getParsedContent();
        sourceRecords.add(source);
      }
    });
  }

  @Override
  public List<SourceRecord> poll() throws InterruptedException {
    //TODO: Create SourceRecord objects that will be sent the kafka cluster.
    log.debug("Polling...");
    try {
      final List<SourceRecord> sourceRecords = this.sourceRecords;
      this.sourceRecords.clear();
      return sourceRecords;
    } catch (Exception e) {
      log.info("Error while retrieving records, treating as an empty poll. " + e);
      return new ArrayList<>();
    }
  }

  @Override
  public void stop() {
    //TODO: Do whatever is required to stop your task.
    this.streamrClient.unsubscribe(this.sub);
    this.streamrClient.disconnect();
  }

  private Integer selectPartition(Object key, Object value, String orderingKey) {
    //TODO: Do whatever is required to select partition.
    throw new UnsupportedOperationException("This has not been implemented.");
  }
}