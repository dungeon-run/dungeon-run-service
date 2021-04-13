package edu.cnm.deepdive.dungeonrun.configuration;

import java.security.SecureRandom;
import java.util.Random;
import org.springframework.beans.BeansException;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans implements ApplicationContextAware {

  private static ApplicationContext context;

  @Bean
  public ApplicationHome applicationHome() {
    return new ApplicationHome(getClass());
  }

  @Bean
  public Random random() {
    return new SecureRandom();
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

  public static <T> T bean(Class<T> beanType) {
    return context.getBean(beanType);
  }

  public static Object bean(String name) {
    return context.getBean(name);
  }
}
