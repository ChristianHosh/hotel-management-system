package com.chris.hotelmanagementsystem.bed_type;

import com.chris.hotelmanagementsystem.entity.CEntityFacade;
import com.chris.hotelmanagementsystem.entity.error.CxException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class BedTypeFacade extends CEntityFacade<BedType> {

  private final BedTypeRepository repository;

  public BedType findBedTypeByName(String name) {
    return repository.findBedTypeByName(name)
        .orElseThrow(() -> CxException.notFound(entityClass(), "name", name));
  }
}
