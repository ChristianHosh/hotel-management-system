package com.chris.hotelmanagementsystem.bed_type;

import com.chris.hotelmanagementsystem.entity.CEntityRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BedTypeRepository extends CEntityRepository<BedType> {
    @Query("select b from BedType b where b.name = :name")
    Optional<BedType> findBedTypeByName(String name);
}
