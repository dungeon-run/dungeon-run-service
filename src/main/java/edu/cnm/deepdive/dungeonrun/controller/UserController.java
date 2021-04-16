package edu.cnm.deepdive.dungeonrun.controller;

import edu.cnm.deepdive.dungeonrun.model.entity.User;
import edu.cnm.deepdive.dungeonrun.service.UserService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController is for use with the client side to push information in the database
 * for use in the client application.
 */
@RestController
@RequestMapping("/users")
@ExposesResourceFor(User.class)
public class UserController {

  private final UserService userService;

  /**
   * Controlls the User entity for use in the service to be sent to the client.
   * @param userService is the service for the client to use.
   */
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Gets the user information for use on the client side or on the webservice.
   * @param auth Needs the auth in order to make sure the connected user is legitimate.
   * @return Returns the user information.
   */
  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  public User me(Authentication auth) {
    return (User) auth.getPrincipal();
  }

  /**
   * Gets the id to be used for the service with the client.
   * @param id A place holder which would show the user ID instead of id.
   * @param auth Needs the auth in order to make sure the connected user is legitimate.
   * @return returns the user information or else throws if the connection is not valid.
   */
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public User get(@PathVariable UUID id, Authentication auth) {
    return userService
        .get(id)
        .orElseThrow();
  }
}
