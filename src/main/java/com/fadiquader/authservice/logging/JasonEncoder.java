package com.fadiquader.authservice.logging;

import net.logstash.logback.argument.StructuredArgument;
import net.logstash.logback.argument.StructuredArguments;
import net.logstash.logback.encoder.LogstashEncoder;

public class JasonEncoder extends LogstashEncoder {
  public static StructuredArgument kv(String key, Object value) {
    return StructuredArguments.keyValue(key, value);
  }

  public static StructuredArgument v(String key, Object value) {
    return StructuredArguments.value(key, value);
  }
}
