package edu.cnm.deepdive.dungeonrun.model.dao;

import edu.cnm.deepdive.dungeonrun.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * To query and find by OauthKey.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findFirstByOauthKey(String oauthKey);

}
