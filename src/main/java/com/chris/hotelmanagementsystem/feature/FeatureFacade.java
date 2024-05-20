package com.chris.hotelmanagementsystem.feature;

import com.chris.hotelmanagementsystem.entity.CEntityFacade;
import com.chris.hotelmanagementsystem.entity.OEntityRepository;
import com.chris.hotelmanagementsystem.entity.error.CxException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeatureFacade extends CEntityFacade<Feature> {

  private final FeatureRepository repository;

  @Override
  public OEntityRepository<Feature> repository() {
    return repository;
  }

  @Override
  public Class<Feature> entityClass() {
    return Feature.class;
  }

  public Feature findFeatureByName(String name) {
    return repository.findFeatureByName(name)
        .orElseThrow(() -> CxException.notFound(entityClass(), "name", name));
  }
}
