package com.chris.hotelmanagementsystem.addon;

import com.chris.hotelmanagementsystem.entity.OEntityRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface AddonRepository extends OEntityRepository<Addon> {
  @Query("select a from Addon a where a.name = :name")
  Optional<Addon> findAddonByName(String name);

}
