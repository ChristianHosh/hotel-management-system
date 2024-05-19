package com.chris.hotelmanagementsystem.entity;

import lombok.Getter;

@Getter
public abstract class SpecEntityResponse extends CEntityResponse {

  private final String name;

  protected SpecEntityResponse(SpecEntity entity) {
    super(entity);
    this.name = entity.getName();
  }
}
