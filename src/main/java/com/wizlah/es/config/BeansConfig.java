package com.wizlah.es.config;

import com.wizlah.es.commons.TokenUtils;
import com.wizlah.es.commons.ValidateManager;
import net.sf.oval.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class BeansConfig {

  @Bean
  public TokenUtils tokenUtils() {
    return new TokenUtils();
  }

  @Bean
  @Lazy
  public Validator validator() {
    return new Validator();
  }

  @Bean
  @Lazy
  public ValidateManager validateManager() {
    return new ValidateManager();
  }
}
