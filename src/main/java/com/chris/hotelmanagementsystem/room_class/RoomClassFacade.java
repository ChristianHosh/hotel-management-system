package com.chris.hotelmanagementsystem.room_class;

import com.chris.hotelmanagementsystem.entity.CEntityFacade;
import com.chris.hotelmanagementsystem.entity.OEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomClassFacade extends CEntityFacade<RoomClass> {

  private final RoomClassRepository repository;

  @Override
  public OEntityRepository<RoomClass> repository() {
    return repository;
  }

  @Override
  public Class<RoomClass> entityClass() {
    return RoomClass.class;
  }

}
