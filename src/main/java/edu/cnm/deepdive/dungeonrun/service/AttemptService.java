package edu.cnm.deepdive.dungeonrun.service;

import edu.cnm.deepdive.dungeonrun.model.dao.AttemptRepository;
import edu.cnm.deepdive.dungeonrun.model.entity.Attempt;
import edu.cnm.deepdive.dungeonrun.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AttemptService {

  private final AttemptRepository attemptRepository;

  public AttemptService(AttemptRepository attemptRepository) {
    this.attemptRepository = attemptRepository;
  }

  public Optional<Attempt> get(@NonNull UUID id) {
    return attemptRepository.findById(id);
  }

  public Attempt newAttempt(Attempt attempt, User user) {
    attempt.setUser(user);
    return attemptRepository.save(attempt);
  }

  public Iterable<Attempt> leaderList() {
    int difficulty = 0;
    return attemptRepository.getAllByDifficultyAndCompletedIsTrueOrderByTimeElapsedAsc(difficulty);
  }

  public Attempt save(@NonNull Attempt attempt) {
    return attemptRepository.save(attempt);
  }

}
