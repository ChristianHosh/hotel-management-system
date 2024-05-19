package com.chris.hotelmanagementsystem.entity;

import com.chris.hotelmanagementsystem.entity.annotations.Keyword;
import com.chris.hotelmanagementsystem.entity.annotations.Unique;
import io.swagger.v3.core.util.ReflectionUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

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
public abstract class CEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @CreationTimestamp
  @Column(name = "created_on", nullable = false, updatable = false)
  private LocalDateTime createdOn;

  @UpdateTimestamp
  @Column(name = "updated_on", nullable = false)
  private LocalDateTime updatedOn;

  @Getter(AccessLevel.NONE)
  @Column(name = "keyword")
  private String keyword;

  @PrePersist
  @PreUpdate
  private void prePersist() {
    preSave();

    generateKeyword();
  }

  private void generateKeyword() {
    var joiner = new StringJoiner("~");
    for (Field field : ReflectionUtils.getDeclaredFields(this.getClass())) {
      field.trySetAccessible();
      if (field.isAnnotationPresent(Keyword.class)) {
        try {
          Object value = field.get(this);
          joiner.add(switch (value) {
            case SpecEntity specEntity -> specEntity.getName();
            case LocalDateTime localDateTime ->
                localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            case LocalDate localDate -> localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            case LocalTime localTime -> localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            case null -> "";
            default -> String.valueOf(value);
          });
        } catch (Exception e) {
          log.warn("failed to add field {} to keyword", field.getName(), e);
        }
      }
    }

    this.keyword = joiner.toString();
  }

  /**
   * called before saving the entity, to be implemented in subclasses,
   * can be used to calculate fields depending on other data
   */
  public void preSave() {

  }

  public final void doValidate() {
    validate();
  }

  /**
   * validates the entity's fields, to be implemented in subclasses
   */
  protected void validate() {

  }

  <T extends CEntity> List<SpecWrapper<T>> checkUnique() throws IllegalAccessException {
    List<SpecWrapper<T>> specifications = new ArrayList<>();
    for (Field field : ReflectionUtils.getDeclaredFields(this.getClass())) {
      field.trySetAccessible();
      if (field.isAnnotationPresent(Unique.class)) {
        Object thisValue = field.get(this);
        Specification<T> tSpecification = (root, query, builder) ->
            builder.and(builder.equal(root.get(field.getName()), thisValue), builder.notEqual(root.get("id"), this.id));
        specifications.add(new SpecWrapper<>(tSpecification, field.getName(), thisValue));
      }
    }
    return specifications;
  }

  record SpecWrapper<T extends CEntity>(Specification<T> spec, String fieldName, Object value) {
  }
}