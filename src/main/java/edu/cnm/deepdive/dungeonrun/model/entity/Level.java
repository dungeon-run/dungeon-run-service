package edu.cnm.deepdive.dungeonrun.model.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

@SuppressWarnings({"JpaDataSourceORMInspection"})
@Entity
@Table(
    indexes = {
        @Index(columnList = "difficulty, endTime"),
    }
)
public class Level {

  @NonNull
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "level_id", nullable = false, updatable = false, columnDefinition = "CHAR(16) FOR BIT DATA")
  private UUID id;

  @NonNull
  @ManyToOne (optional = false)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date startTime;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date endTime;

  @NonNull
  @Column(nullable = false, updatable = false)
  private int timeGiven;

  @NonNull
  @Column(nullable = false, updatable = false)
  private int difficulty;

  private boolean completed;

  @NonNull
  public UUID getId() {
    return id;
  }

  @NonNull
  public User getUser() {
    return user;
  }

  public void setUser(@NonNull User user) {
    this.user = user;
  }

  @NonNull
  public Date getStartTime() {
    return startTime;
  }

  @NonNull
  public Date getEndTime() {
    return endTime;
  }

  @NonNull
  public int getTimeGiven() {
    return timeGiven;
  }

  public void setTimeGiven(@NonNull int timeGiven) {
    this.timeGiven = timeGiven;
  }

  @NonNull
  public int getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(@NonNull int difficulty) {
    this.difficulty = difficulty;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }
}
