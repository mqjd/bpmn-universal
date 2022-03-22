package com.mqjd.sink;

import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogSink implements SinkFunction<Long> {
  private static final Logger LOG = LoggerFactory.getLogger(LogSink.class);

  @Override
  public void invoke(Long value, Context context) {
    LOG.info(String.valueOf(value));
  }
}
