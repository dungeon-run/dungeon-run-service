package edu.cnm.deepdive.dungeonrun.controller;

import edu.cnm.deepdive.dungeonrun.model.entity.Level;
import edu.cnm.deepdive.dungeonrun.service.LevelService;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/levels")
@ExposesResourceFor(Level.class)
public class LevelController {

  private final LevelService levelService;

  public LevelController() {
    levelService = null;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Level> list(Authentication auth) {
    return levelService.list();
  }

}
