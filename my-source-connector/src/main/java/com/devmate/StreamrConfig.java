package com.devmate;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;


public class StreamrConfig extends AbstractConfig {
  private static final String BASE_URL_DOC = "https://thanhle.blog";

  public static final String PRIVATE_KEY_CONFIG = "streamr.privateKey";
  private static final String PRIVATE_KEY_DOC = "This is a setting important to my connector.";

  public static final String STREAM_ID_CONFIG = "streamr.streamId";
  private static final String STREAM_ID_DOC = "This is a setting important to my connector.";

  public StreamrConfig(ConfigDef config, Map<String, String> parsedConfig) {
    super(config, parsedConfig);
  }

  public StreamrConfig(Map<String, String> parsedConfig) {
    this(conf(), parsedConfig);
  }

  public static ConfigDef conf() {
    // TODO: Define partition config, resent option
    return new ConfigDef()
        .define(PRIVATE_KEY_CONFIG, ConfigDef.Type.STRING, ConfigDef.NO_DEFAULT_VALUE, new NonEmptyStringWithoutControlChars(), ConfigDef.Importance.HIGH, PRIVATE_KEY_DOC)
        .define(STREAM_ID_CONFIG, ConfigDef.Type.STRING, ConfigDef.NO_DEFAULT_VALUE, new NonEmptyStringWithoutControlChars(), ConfigDef.Importance.HIGH, STREAM_ID_DOC);
  }

  public String getPrivateKeyConfig(){
    return this.getString(PRIVATE_KEY_CONFIG);
  }

  public String getStreamIdConfig(){
    return this.getString(PRIVATE_KEY_CONFIG);
  }
}

final class NonEmptyStringWithoutControlChars extends ConfigDef.NonEmptyStringWithoutControlChars {
  //Only here to create nice human readable for exporting to documentation.
  @Override
  public String toString() {
    return "non-empty string and no ISO control characters";
  }
}

