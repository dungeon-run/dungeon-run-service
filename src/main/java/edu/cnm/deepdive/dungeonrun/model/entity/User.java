package edu.cnm.deepdive.dungeonrun.model.entity;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
    name = "user_profile",
    indexes = {
        @Index(columnList = "created"),
        @Index(columnList = "connected"),
    }
)
public class User {

  @NonNull
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "user_id", nullable = false, updatable = false, columnDefinition = "CHAR(16) FOR BIT DATA")
  private UUID id;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @NonNull
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date connected;

  @NonNull
  @Column(nullable = false, updatable = false,unique = true)
  private String oauthKey;

  @NonNull
  @Column(nullable = false)
  private String name;

  @NonNull
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("difficulty DESC, endTime DESC")
  private final List<Level> levels = new LinkedList<>();

  @NonNull
  public UUID getId() {
    return id;
  }

  @NonNull
  public Date getCreated() {
    return created;
  }

  @NonNull
  public Date getConnected() {
    return connected;
  }

  @NonNull
  public String getOauthKey() {
    return oauthKey;
  }

  public void setOauthKey(@NonNull String oauthKey) {
    this.oauthKey = oauthKey;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }
}
