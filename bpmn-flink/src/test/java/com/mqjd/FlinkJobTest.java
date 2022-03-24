package com.mqjd;

import com.mqjd.sink.LogSink;
import com.mqjd.source.InfiniteLongSource;
import com.mqjd.transform.IncrementMapFunction;
import org.apache.flink.configuration.*;
import org.apache.flink.runtime.testutils.MiniClusterResourceConfiguration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.test.util.MiniClusterWithClientResource;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/** FlinkJobTest */
public class FlinkJobTest {

  private static final Logger LOG = LoggerFactory.getLogger(FlinkJobTest.class);
  private static final Configuration configuration = new Configuration();

  static {
    configuration.set(CoreOptions.DEFAULT_PARALLELISM, 1);
    configuration.set(TaskManagerOptions.TOTAL_PROCESS_MEMORY, MemorySize.parse("1gb"));
    configuration.set(RestOptions.ENABLE_FLAMEGRAPH, true);
  }

  @ClassRule
  public static MiniClusterWithClientResource flinkCluster =
      new MiniClusterWithClientResource(
          new MiniClusterResourceConfiguration.Builder()
              .setNumberSlotsPerTaskManager(2)
              .setNumberTaskManagers(1)
              .setConfiguration(configuration)
              .build());

  @Test
  public void simpleFlinkJobTest() throws Exception {
    LOG.info("start test");
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    DataStreamSource<Long> source = env.addSource(new InfiniteLongSource());
    SingleOutputStreamOperator<Long> map = source.map(new IncrementMapFunction());
    map.addSink(new LogSink());
    env.execute("Simple Test");
  }
}
