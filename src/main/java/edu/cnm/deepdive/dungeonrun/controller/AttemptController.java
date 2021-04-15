package edu.cnm.deepdive.dungeonrun.controller;

import edu.cnm.deepdive.dungeonrun.model.entity.Attempt;
import edu.cnm.deepdive.dungeonrun.model.entity.User;
import edu.cnm.deepdive.dungeonrun.service.AttemptService;
import java.util.UUID;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/levels")
@ExposesResourceFor(Attempt.class)
public class AttemptController {

  private final AttemptService attemptService;

  public AttemptController(AttemptService attemptService) {
    this.attemptService = attemptService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Attempt> leaderList(Authentication auth) {
    return attemptService.leaderList();
  }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Attempt> post(@RequestBody Attempt attempt, Authentication auth) {
    attempt = attemptService.newAttempt(attempt, (User) auth.getPrincipal());
      return ResponseEntity.created(attempt.getHref()).body(attempt);
  }

  @PutMapping(value = "/{id}/completed", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public boolean update(@PathVariable UUID id, @RequestBody(required = false) Boolean completed,
      Authentication auth) {
    return attemptService
        .get(id)
        .map((attempt) -> { //replacing the current level with the updated timestamp
          if (attempt.getUser().getId().equals(((User) auth.getPrincipal()).getId())) {
            attempt.setCompleted((completed != null) ? completed : true);
            return attemptService.save(attempt)
                .isCompleted(); //ensures the user completing the level is the same as the one who started it
          } else {
            return null;
          }
        })
        .orElseThrow();
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Attempt get(@PathVariable UUID id, Authentication auth) {
    return attemptService
        .get(id)
        .map((attempt) -> attempt.getUser().getId().equals(((User) auth.getPrincipal()).getId())
            ? attempt
            : null)
        .orElseThrow();
  }
}
