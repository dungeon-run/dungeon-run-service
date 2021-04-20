package edu.cnm.deepdive.dungeonrun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

/**
 * Author
 * Hana Dawson and Kaz Penn
 * DungeonRun is an application game.
 * The game will consist of randomly generated levels that a user must complete
 * within an allotted amount of time based on the level of difficulty.
 */
@SpringBootApplication
@EnableHypermediaSupport(type = {HypermediaType.HAL})
public class DungeonRunApplication {

  /**
   * Main class for the application for use.
   * @param args for
   */
  public static void main(String[] args) {
    SpringApplication.run(DungeonRunApplication.class, args);
  }

}
