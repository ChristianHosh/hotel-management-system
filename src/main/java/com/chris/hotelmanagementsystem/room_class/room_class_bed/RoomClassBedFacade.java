package com.chris.hotelmanagementsystem.room_class.room_class_bed;

import com.chris.hotelmanagementsystem.entity.CEntityFacade;
import com.chris.hotelmanagementsystem.entity.OEntityRepository;
import com.chris.hotelmanagementsystem.room_class.RoomClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomClassBedFacade extends CEntityFacade<RoomClassBed> {

  private final RoomClassBedRepository repository;

  @Override
  public OEntityRepository<RoomClassBed> repository() {
    return repository;
  }

  @Override
  public Class<RoomClassBed> entityClass() {
    return RoomClassBed.class;
  }

  public Page<RoomClassBed> findByRoomClass(RoomClass roomClass, String query, Pageable pageable) {
    return repository.findByRoomClass(roomClass, query, pageable);
  }
}
