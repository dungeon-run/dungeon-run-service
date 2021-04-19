package edu.cnm.deepdive.dungeonrun.service;

import edu.cnm.deepdive.dungeonrun.model.dao.UserRepository;
import edu.cnm.deepdive.dungeonrun.model.entity.User;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

/**
 * Repository for User services to communicate with client and be able to obtain the
 * necessary columns needed to track data for the application.
 */
@Service
public class UserService implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

  private final UserRepository repository;

  /**
   * Encapsulates the logic required to access data sources from the attempt entity.
   * @param repository Gathers the information to be used in other classes.
   */
  @Autowired
  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  /**
   * Sets up the user id for a user logging in, or creates a new user id if it is a user's first time
   * logging in to the application.
   * @param oauthKey Gets the oauthKey from to secure the connection.
   * @param name Gets the name the user has set for themselves.
   * @return Returns the id information to save to the database.
   */
  public User getOrCreate(String oauthKey, String name) {
    return repository.findFirstByOauthKey(oauthKey)
        .map((user) -> {
          user.setConnected(new Date());
          return repository.save(user);
        })
        .orElseGet(() -> {
          User user = new User();
          user.setOauthKey(oauthKey);
          user.setName(name);
          return repository.save(user);
        });
  }

  @Override
  public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
    Collection<SimpleGrantedAuthority> grants =
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    return new UsernamePasswordAuthenticationToken(
        getOrCreate(jwt.getSubject(), jwt.getClaim("name")), jwt.getTokenValue(),grants);
  }

  /**
   * Gets the repository to use for other classes when needed.
   * @return Returns the repository for User class when called upon.
   */
  public UserRepository getRepository() {
    return repository;
  }

  /**
   * User id is optional for return to the database.
   * @param id The UUID id is for use to assign database information
   * @return Finds the repository information associated to a user id.
   */
  public Optional<User> get(UUID id) {
    return repository.findById(id);
  }
}
