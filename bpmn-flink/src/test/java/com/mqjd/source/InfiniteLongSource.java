package com.mqjd.source;

import com.mqjd.FlinkJobTest;
import org.apache.commons.lang3.RandomUtils;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InfiniteLongSource implements SourceFunction<Long> {
  private static final Logger LOG = LoggerFactory.getLogger(FlinkJobTest.class);
  private boolean running = true;

  @Override
  public void run(SourceContext<Long> ctx) throws InterruptedException {
    while (running) {
      Thread.sleep(100L);
      ctx.collect(RandomUtils.nextLong(0, 100));
    }
  }

  @Override
  public void cancel() {
    running = false;
  }
}
