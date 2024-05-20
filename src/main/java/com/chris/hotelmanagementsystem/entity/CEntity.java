package com.chris.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SoftDelete;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract base class for entities in the system, providing common fields and functionality.
 * This class includes fields for entity identification, creation and update timestamps, and a keyword for search optimization.
 * It also defines lifecycle hooks for pre-persistence actions and methods for generating a search keyword and checking field uniqueness.
 * <p>
 * Fields:
 * <ul>
 * <li>id: Unique identifier for the entity.</li>
 * <li>createdOn: Timestamp indicating when the entity was created.</li>
 * <li>updatedOn: Timestamp indicating when the entity was last updated.</li>
 * <li>keyword: A generated string used for search optimization, not directly exposed via getter.</li>
 * </ul>
 */
@Slf4j
@Getter
@SoftDelete(columnName = "deleted")
@MappedSuperclass
public abstract class CEntity extends OEntity implements Comparable<CEntity> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "c_id", nullable = false)
  private Long id;

  @Override
  public int compareTo(@NotNull CEntity o) {
    return compareToImpl(o);
  }

  public int compareToImpl(@NotNull CEntity o) {
    return Long.compare(this.id, o.id);
  }
}