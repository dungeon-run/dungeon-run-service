package edu.cnm.deepdive.dungeonrun.model.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    name = "level_id",
    indexes = {
        @Index(columnList = "startTime"),
        @Index(columnList = "endTime"),
    }
)
public class Level {

  @NonNull
  @Id
  @GeneratedValue(generator = "long")
  @GenericGenerator(name = "long", strategy = "long")
  @Column(name = "level_id", nullable = false, updatable = false, columnDefinition = "CHAR(16) FOR BIT DATA")
  private long id;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date startTime;

  @NonNull
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date endTime;

  @NonNull
  @Column(nullable = false, updatable = false,unique = true)
  private int timeGiven;

  @NonNull
  @Column(nullable = false)
  private int difficulty;

  @NonNull
  public long getId() {
    return id;
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
}
