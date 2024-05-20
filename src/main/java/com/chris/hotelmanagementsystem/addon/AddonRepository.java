package com.chris.hotelmanagementsystem.addon;

import com.chris.hotelmanagementsystem.entity.CEntityRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface AddonRepository extends CEntityRepository<Addon> {
  @Query("select a from Addon a where a.name = :name")
  Optional<Addon> findAddonByName(String name);

}
