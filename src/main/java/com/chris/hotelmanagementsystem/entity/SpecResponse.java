package com.chris.hotelmanagementsystem.entity;

import lombok.Getter;

@Getter
public class SpecResponse extends CEntityResponse {

  private final String name;

  public SpecResponse(SpecEntity entity) {
    super(entity);
    this.name = entity.getName();
  }
}
