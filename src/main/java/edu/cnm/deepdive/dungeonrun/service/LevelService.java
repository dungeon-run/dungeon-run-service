package edu.cnm.deepdive.dungeonrun.service;

import edu.cnm.deepdive.dungeonrun.model.dao.LevelRepository;
import edu.cnm.deepdive.dungeonrun.model.entity.Attempt;
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

  public Optional<Attempt> get(@NonNull UUID id) {
    return levelRepository.findById(id);
  }

  public Attempt newLevel(Attempt attempt, User user) {
    attempt.setUser(user);
    return levelRepository.save(attempt);
  }

  public Iterable<Attempt> leaderList() {
    int difficulty = 0;
    return levelRepository.getAllByDifficultyAndCompletedIsTrueOrderByTimeElapsedAsc(difficulty);
  }

  public Attempt save(@NonNull Attempt attempt) {
    return levelRepository.save(attempt);
  }

}
