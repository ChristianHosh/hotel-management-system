package com.chris.hotelmanagementsystem.feature;

import com.chris.hotelmanagementsystem.entity.CEntityFacade;
import com.chris.hotelmanagementsystem.entity.error.CxException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class FeatureFacade extends CEntityFacade<Feature> {

  private final FeatureRepository repository;

  public Feature findFeatureByName(String name) {
    return repository.findFeatureByName(name)
        .orElseThrow(() -> CxException.notFound(entityClass(), "name", name));
  }
}
