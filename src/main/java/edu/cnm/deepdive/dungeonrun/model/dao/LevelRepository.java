package edu.cnm.deepdive.dungeonrun.model.dao;

import edu.cnm.deepdive.dungeonrun.model.entity.Level;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, UUID> {

  Iterable<Level> getAllByOrderByDifficultyDesc();

  //TODO Discuss how we want the query for this to show.

}

