package edu.cnm.deepdive.dungeonrun.configuration;

import java.security.SecureRandom;
import java.util.Random;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

  @Bean
  public Random random() {
    return new SecureRandom();
  }

}
