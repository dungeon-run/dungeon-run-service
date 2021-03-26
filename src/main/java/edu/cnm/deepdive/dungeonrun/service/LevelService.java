package edu.cnm.deepdive.dungeonrun.service;

import edu.cnm.deepdive.dungeonrun.model.dao.LevelRepository;
import edu.cnm.deepdive.dungeonrun.model.entity.Level;
import java.util.Optional;
import java.util.UUID;
import org.springframework.lang.NonNull;

public class LevelService {

  private final LevelRepository levelRepository;

  public LevelService(LevelRepository levelRepository) {
    this.levelRepository = levelRepository;
  }

  public Optional<Level> get(@NonNull UUID id) {
    return levelRepository.findById(id);
  }

  public Iterable<Level> list() {
    return levelRepository.getAllByOrderByCreatedDesc();
  }

  public Level save(@NonNull Level level) {
    return levelRepository.save(level);
  }


}
