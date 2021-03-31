package edu.cnm.deepdive.dungeonrun.model.dao;

import edu.cnm.deepdive.dungeonrun.model.entity.Level;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * LevelRepository will allow us to generate the database in the order we wish to see it.
 * In this case, Ordering from difficulty descending.
 */
public interface LevelRepository extends JpaRepository<Level, UUID> {

  Iterable<Level> getAllByOrderByDifficultyDesc();

  //TODO Discuss how we want the query for this to show.
  // Need to add to receive endTime in here too ?

}

