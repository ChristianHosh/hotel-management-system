package com.chris.hotelmanagementsystem.bed_type;

import com.chris.hotelmanagementsystem.entity.OEntityRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface BedTypeRepository extends OEntityRepository<BedType> {

  @Query("select b from BedType b where b.name = :name")
  Optional<BedType> findBedTypeByName(String name);

}
