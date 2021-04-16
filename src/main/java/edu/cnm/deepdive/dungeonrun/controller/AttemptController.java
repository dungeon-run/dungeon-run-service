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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AttemptController is for use with the client side to push information in the database
 * for use in the client application.
 */
@RestController
@RequestMapping("/attempts")
@ExposesResourceFor(Attempt.class)
public class AttemptController {

  private final AttemptService attemptService;

  /**
   * Controlls the database for attempts entity.
   * @param attemptService Is the service for the attempts entity.
   */
  public AttemptController(AttemptService attemptService) {
    this.attemptService = attemptService;
  }

  /**
   * Sets the header to be sent over to the client for attempts.
   * @param difficulty Is an integer set by the user which changes the size of the maze.
   * @param auth Needs the auth in order to make sure the connected user is legitimate.
   * @return Returns the difficulty for use in the leaderboard.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Attempt> leaderList(@RequestParam int difficulty, Authentication auth) {
    return attemptService.leaderList(difficulty);
  }

  /**
   * Body of the attempt service, to be sent over to the client side when needed.
   * @param attempt An instance of the attempt entity.
   * @param auth Needs the auth in order to make sure the connected user is legitimate.
   * @return Returns the body of the service for use on the client side.
   */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Attempt> post(@RequestBody Attempt attempt, Authentication auth) {
    attempt = attemptService.newAttempt(attempt, (User) auth.getPrincipal());
      return ResponseEntity.created(attempt.getHref()).body(attempt);
  }

  /**
   * Puts the completed attempts in the body for use on the client side.
   * @param id The id of the current attempt generated.
   * @param completed Returns true when the end of the maze is successfully reached.
   * @param auth Needs the auth in order to make sure the connected user is legitimate.
   * @return Saves the attempt when it is completed for use in the leaderboard on the client side.
   */
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

  /**
   * Gets the user id to ensure the information is saved to the right path for the user.
   * @param id Using the user id associated with the current logged in user.
   * @param auth Needs the auth in order to make sure the connected user is legitimate.
   * @return Returns the user information or throws an exception if id is not valid.
   */
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
