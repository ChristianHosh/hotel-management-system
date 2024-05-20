package com.chris.hotelmanagementsystem.feature;

import com.chris.hotelmanagementsystem.entity.OEntityRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface FeatureRepository extends OEntityRepository<Feature> {
  @Query("select f from Feature f where f.name = :name")
  Optional<Feature> findFeatureByName(String name);
}
