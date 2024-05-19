package com.chris.hotelmanagementsystem.entity;

import com.chris.hotelmanagementsystem.entity.annotations.Keyword;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class SpecEntity extends CEntity {
  
  @Keyword
  @Column(name = "name", nullable = false)
  private String name;
  
  
}