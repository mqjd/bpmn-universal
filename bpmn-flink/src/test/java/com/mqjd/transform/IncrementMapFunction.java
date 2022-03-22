package com.mqjd.transform;

import org.apache.flink.api.common.functions.MapFunction;

public class IncrementMapFunction implements MapFunction<Long, Long> {
  @Override
  public Long map(Long value) {
    return value + 1;
  }
}
