package edu.cnm.deepdive.dungeonrun.model.dao;

import edu.cnm.deepdive.dungeonrun.model.entity.Attempt;
import java.util.Date;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AttemptRepository will allow us to generate the database and desired columns. This will be used
 * on the client side to show the leaderboard stats for users.
 */
public interface AttemptRepository extends JpaRepository<Attempt, UUID> {

  /**
   * Querys the information needed to set up the leaderboard. Getting all levels by difficulty,
   * has to be completed, and order by time elapsed in Ascending order.
   * @param difficulty The leaderboard will be organized to show results based on difficulty level
   *                   selected on the client side.
   * @return Returns all information for the requested query.
   */
  Iterable<Attempt> getAllByDifficultyAndCompletedIsTrueOrderByTimeElapsedAsc(int difficulty);

}

