package com.chris.hotelmanagementsystem.floor;

import com.chris.hotelmanagementsystem.entity.CEntityFacade;
import com.chris.hotelmanagementsystem.entity.OEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FloorFacade extends CEntityFacade<Floor> {

  private final FloorRepository repository;

  @Override
  public OEntityRepository<Floor> repository() {
    return repository;
  }

  @Override
  public Class<Floor> entityClass() {
    return Floor.class;
  }
}
