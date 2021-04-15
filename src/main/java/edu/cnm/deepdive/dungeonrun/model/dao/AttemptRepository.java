package edu.cnm.deepdive.dungeonrun.model.dao;

import edu.cnm.deepdive.dungeonrun.model.entity.Attempt;
import java.util.Date;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * LevelRepository will allow us to generate the database in the order we wish to see it.
 * In this case, Ordering from difficulty descending.
 */
public interface AttemptRepository extends JpaRepository<Attempt, UUID> {

  Iterable<Attempt> getAllByDifficultyAndCompletedIsTrueOrderByTimeElapsedAsc(int difficulty);

  // TODO getAllByDifficultyAndCompletedIsTrueOrderByTimeElapsedAsc(int difficulty)

}

