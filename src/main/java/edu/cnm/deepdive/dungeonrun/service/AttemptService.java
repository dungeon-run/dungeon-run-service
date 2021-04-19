package edu.cnm.deepdive.dungeonrun.service;

import edu.cnm.deepdive.dungeonrun.model.dao.AttemptRepository;
import edu.cnm.deepdive.dungeonrun.model.entity.Attempt;
import edu.cnm.deepdive.dungeonrun.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Repository for Attempts services to communicate with client and be able to obtain the
 * necessary columns needed to track data for the application.
 */
@Service
public class AttemptService {

  private final AttemptRepository attemptRepository;

  /**
   * Encapsulates the logic required to access data sources from the attempt entity.
   * @param attemptRepository Gathers the information to be used in other classes.
   */
  public AttemptService(AttemptRepository attemptRepository) {
    this.attemptRepository = attemptRepository;
  }

  /**
   * The id for the attempt can be option to return in the database.
   * @param id Parameters are the UUID which is generated upon each attempt created.
   * @return returns the attempts tied to the the attempt id.
   */
  public Optional<Attempt> get(@NonNull UUID id) {
    return attemptRepository.findById(id);
  }

  /**
   * Sets up the database to generate the information needed when a new attempt is started.
   */
  public Attempt newAttempt(Attempt attempt, User user) {
    attempt.setUser(user);
    return attemptRepository.save(attempt);
  }

  /**
   * Encapsulates all of the database information needed to set up the leader board in the
   * client side of the application.
   * @param difficulty Difficulty of the attempts is used as parameter to gain the associated information
   *                   to show up on the leaderboard. SUch as getting the user and time elapsed associated
   *                   with the difficulty level.
   * @return Returns the associated columns assigned with to the difficulty for attempts.
   */
  public Iterable<Attempt> leaderList(int difficulty) {
    return attemptRepository.getAllByDifficultyAndCompletedIsTrueOrderByTimeElapsedAsc(difficulty);
  }

  /**
   * Saves the attempts to the database when the conditions are met.
   * @param attempt Creates an instance of the class Attempt to be saved to the database with
   *                associated columns.
   * @return Returns the save attempt.
   */
  public Attempt save(@NonNull Attempt attempt) {
    return attemptRepository.save(attempt);
  }

}
