package com.chris.hotelmanagementsystem.entity;

import com.chris.hotelmanagementsystem.entity.annotations.Keyword;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a specialized entity that extends the {@link CEntity} class.
 * This abstract class serves as a base for entities that require a name field,
 * ensuring consistency and providing common functionality across different entity types.
 * It is annotated with {@link MappedSuperclass} to indicate that its properties are
 * to be inherited by its subclasses which are entities in their own right.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class SpecEntity extends CEntity {
  
  @Keyword
  @Column(name = "name", nullable = false)
  private String name;
  
  
}