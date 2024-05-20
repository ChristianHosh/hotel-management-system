package com.chris.hotelmanagementsystem.entity;

import com.chris.hotelmanagementsystem.entity.annotations.Keyword;
import com.chris.hotelmanagementsystem.entity.annotations.Unique;
import io.swagger.v3.core.util.ReflectionUtils;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Slf4j
@Getter
@MappedSuperclass
public abstract class OEntity implements Serializable {

  public abstract Object getId();

  @CreationTimestamp
  @Column(name = "c_created_on", nullable = false, updatable = false)
  private LocalDateTime createdOn;

  @UpdateTimestamp
  @Column(name = "c_updated_on", nullable = false)
  private LocalDateTime updatedOn;

  @Getter(AccessLevel.NONE)
  @Column(name = "c_keyword")
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

  <T extends OEntity> List<SpecWrapper<T>> checkUnique() throws IllegalAccessException {
    List<SpecWrapper<T>> specifications = new ArrayList<>();
    for (Field field : ReflectionUtils.getDeclaredFields(this.getClass())) {
      field.trySetAccessible();
      if (field.isAnnotationPresent(Unique.class)) {
        Object thisValue = field.get(this);
        Specification<T> tSpecification = (root, query, builder) -> {
          if (isNew()) {
            return builder.equal(root.get(field.getName()), thisValue);
          } else {
            return builder.and(
                builder.equal(root.get(field.getName()), thisValue),
                builder.notEqual(root.get("id"), this.getId()));
          }
        };
        specifications.add(new SpecWrapper<>(tSpecification, field.getName(), thisValue));
      }
    }
    return specifications;
  }

  private boolean isNew() {
    return this.getId() == null;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (o instanceof CEntity entity && (this.getClass().equals(entity.getClass()))) {
      return Objects.equals(this.getId(), entity.getId());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(getId());
    result = 31 * result + Objects.hashCode(getClass().getName());
    result = 31 * result + Objects.hashCode(createdOn);
    result = 31 * result + Objects.hashCode(updatedOn);
    return result;
  }

  record SpecWrapper<T extends OEntity>(Specification<T> spec, String fieldName, Object value) {
  }

}
