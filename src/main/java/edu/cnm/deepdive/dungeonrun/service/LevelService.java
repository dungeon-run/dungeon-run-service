package edu.cnm.deepdive.dungeonrun.service;

import edu.cnm.deepdive.dungeonrun.model.dao.LevelRepository;
import edu.cnm.deepdive.dungeonrun.model.entity.Level;
import edu.cnm.deepdive.dungeonrun.model.entity.User;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class LevelService {

  private final LevelRepository levelRepository;

  public LevelService(LevelRepository levelRepository) {
    this.levelRepository = levelRepository;
  }

  public Optional<Level> get(@NonNull UUID id) {
    return levelRepository.findById(id);
  }

  public Level newLevel(Level level, User user) {
    level.setUser(user);
    return levelRepository.save(level);
  }

  public Iterable<Level> finalList() {
    return levelRepository.getAllByOrderByDifficulty();
  }

  public Iterable<Level> leaderList() {
    Date endTime = new Date();
    int difficulty = 0;
    return levelRepository.getAllByCompletedIsTrueAndEndTimeAndDifficultyOrderByEndTime(
        endTime, difficulty);
  }

  public Level save(@NonNull Level level) {
    return levelRepository.save(level);
  }

}
