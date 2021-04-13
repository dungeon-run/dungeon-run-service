package edu.cnm.deepdive.dungeonrun.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.net.URI;
import java.util.Date;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Level entity to hold the database of levels generated and completed.
 * Indexes for difficulty and endTime will be indexed since this is a metric
 * that will be used for the implementation of leaderboards.
 */

@SuppressWarnings({"JpaDataSourceORMInspection"})
@Entity
@Table(
    name = "game_level",
    indexes = {
        @Index(columnList = "difficulty, endTime"),
    }
)
@JsonIgnoreProperties(
    value = {
        "id", "startTime", "endTime"},
    allowGetters = true, ignoreUnknown = true
)
@Component
public class Level {

  private static EntityLinks entityLinks;

/**
 * Generates the UUID for levels. Levels are to be randomly generated each time a new game is started.
 **/
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "level_id", nullable = false, updatable = false, columnDefinition = "CHAR(16) FOR BIT DATA")
  private UUID id;

  /**
   * There may be many levels associated to one User.
   */
  @NonNull
  @ManyToOne (optional = false)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  /**
   * When a new level is generated, a time stamp will generate with it.
   */
  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date startTime;

  /**
   * When the level is completed successfully an endTime stamp will be generated.
   * The endTime will be used against the time given to determine the users ranking in the leaderboard.
   */
  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date endTime;

  /**
   * Each level generated will have a set amount of time given depending on difficulty set.
   */
  @NonNull
  @Column(nullable = false, updatable = false)
  private int timeGiven;

  /**
   * Difficulty will be a range starting from 1-20.
   */
  @NonNull
  @Column(nullable = false, updatable = false)
  private int difficulty;

  /**
   * When the level is completed successfully, it will receive a True boolean.
   */
  private boolean completed;

  /**
   * Receives the id for the generated level.
   */
  @NonNull
  public UUID getId() {
    return id;
  }

  /**
   * receives the user from the user class.
   */
  @NonNull
  public User getUser() {
    return user;
  }

  /**
   * to be used to setUser when needed
   */
  public void setUser(@NonNull User user) {
    this.user = user;
  }

  /**
   * Returns the generated start time for the level.
   */
  @NonNull
  public Date getStartTime() {
    return startTime;
  }

  /**
   * Returns the generated end time for the level when completed.
   */
  @NonNull
  public Date getEndTime() {
    return endTime;
  }

  /**
   * Returns the timeGiven which will be based on difficulty of level.
   */
  @NonNull
  public int getTimeGiven() {
    return timeGiven;
  }

  /**
   * Will setTimeGiven when user starts a level and setTimeGiven will be based on difficulty of level.
   */
  public void setTimeGiven(@NonNull int timeGiven) {
    this.timeGiven = timeGiven;
  }

  /**
   * Receives the difficulty chosen from the user.
   */
  @NonNull
  public int getDifficulty() {
    return difficulty;
  }

  /**
   * sets the difficulty in place when user starts level.
   */
  public void setDifficulty(@NonNull int difficulty) {
    this.difficulty = difficulty;
  }

  /**
   * Will return true when level isCompleted.
   */
  public boolean isCompleted() {
    return completed;
  }

  /**
   *Will set the boolean upon completion.
   */
  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  public URI getHref() {
    return (id != null) ? entityLinks.linkForItemResource(Level.class, id).toUri() : null;
  }

  @Autowired
  public static void setEntityLinks(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") EntityLinks entityLinks) {
    Level.entityLinks = entityLinks;
  }

  @PostConstruct
  private void initHateoas() {
    //noinspection ResultOfMethodCallIgnored
    entityLinks.toString();
  }
}
