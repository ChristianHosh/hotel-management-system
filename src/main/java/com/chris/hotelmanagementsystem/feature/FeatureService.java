package com.chris.hotelmanagementsystem.feature;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class FeatureService {

  private final FeatureFacade featureFacade;

  public Feature.FeatureResponse getFeature(Long id) {
    return featureFacade.findById(id).toResponse();
  }

  public Feature.FeatureResponse deleteFeature(Long id) {
    Feature feature = featureFacade.findById(id);
    return featureFacade.delete(feature).toResponse();
  }

  public Feature.FeatureResponse updateFeature(Long id, Feature.FeatureRequest request) {
    Feature feature = featureFacade.findById(id);
    feature.setName(request.name());

    return featureFacade.save(feature).toResponse();
  }

  public Page<Feature.FeatureResponse> getFeatures(int page, int size, String query) {
    return featureFacade.findAll(query, PageRequest.of(page, size)).map(Feature::fromEntity);
  }

  public Feature.FeatureResponse createFeature(Feature.FeatureRequest request) {
    Feature feature = new Feature();
    feature.setName(request.name());

    return featureFacade.save(feature).toResponse();
  }
}
