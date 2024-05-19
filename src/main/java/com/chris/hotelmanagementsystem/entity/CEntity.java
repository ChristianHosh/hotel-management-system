package com.chris.hotelmanagementsystem.entity;

import com.chris.hotelmanagementsystem.entity.annotations.Keyword;
import com.chris.hotelmanagementsystem.entity.annotations.Unique;
import io.swagger.v3.core.util.ReflectionUtils;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

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
            case LocalDateTime localDateTime -> localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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
  
  public void preSave() {
    
  }
  
  public final void doValidate() {
    validate();
  }
  
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
  
  record SpecWrapper<T extends CEntity>(Specification<T> spec, String fieldName, Object value) {}
}