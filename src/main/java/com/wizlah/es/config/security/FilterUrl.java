package com.wizlah.es.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "filter")
@Data
public class FilterUrl {

  private List<String> urls;
}
