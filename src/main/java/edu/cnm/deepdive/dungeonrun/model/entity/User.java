package edu.cnm.deepdive.dungeonrun.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.cnm.deepdive.dungeonrun.configuration.Beans;
import java.net.URI;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * User Entity Class for database.
 * created and connected will be indexed for a faster query.
 */

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

  /**
   * a uuid will be generated for each user id.
   */
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "user_id", nullable = false, updatable = false, columnDefinition = "CHAR(16) FOR BIT DATA")
  private UUID id;

  /**
   * A timestamp will be created each time a new user is created.
   */
  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  /**
   * The timestamp will update each time a user reconnects/logs in to the server.
   */
  @NonNull
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date connected;

  /**
   * oauthKey allows the server to recognize the user is the same when logging in.
   */
  @NonNull
  @Column(nullable = false, updatable = false,unique = true)
  private String oauthKey;

  /**
   * A user will be able to set a username to display when using the app and for the leaderboards.
   */
  @NonNull
  @Column(nullable = false)
  private String name;

  /**
   * User and Level have a one to many relationship. One user can access many levels.
   */
  @JsonIgnore
  @NonNull
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<Level> levels = new LinkedList<>();

  /**
   *Returns the user id.
   */
  @NonNull
  public UUID getId() {
    return id;
  }

  /**
   * Returns the created timestamp when id is generated for the first time.
   */
  @NonNull
  public Date getCreated() {
    return created;
  }

  /**
   * returns the levels attached with id.
   */
  @NonNull
  public List<Level> getLevels() {
    return levels;
  }

  /**
   * Returns the updated timestamp when user is reconnected.
   */
  @NonNull
  public Date getConnected() {
    return connected;
  }

  /**
   *Sets the updated timestamp.
   */
  public void setConnected(@NonNull Date connected) {
    this.connected = connected;
  }

  /**
   * Returns the generated oauthKey.
   */
  @NonNull
  public String getOauthKey() {
    return oauthKey;
  }

  /**
   * sets the oauthKey when needed.
   */
  public void setOauthKey(@NonNull String oauthKey) {
    this.oauthKey = oauthKey;
  }

  /**
   * Gets the name the user has set.
   */
  @NonNull
  public String getName() {
    return name;
  }

  /**
   *Sets the name the user has set.
   */
  public void setName(@NonNull String name) {
    this.name = name;
  }

  public URI getHref() {
    EntityLinks entityLinks = Beans.bean(EntityLinks.class);
    return (id != null) ? entityLinks.linkForItemResource(User.class, id).toUri() : null;
  }
}
