package com.chris.hotelmanagementsystem.room;

import com.chris.hotelmanagementsystem.entity.CEntityFacade;
import com.chris.hotelmanagementsystem.floor.Floor;
import com.chris.hotelmanagementsystem.room_class.RoomClass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Getter
@Service
@RequiredArgsConstructor
public class RoomFacade extends CEntityFacade<Room> {

  private final RoomRepository repository;

  public Page<Room> findByRoomClass(RoomClass roomClass, String query, Pageable pageable) {
    return repository.findByRoomClass(roomClass, query, pageable);
  }

  public Page<Room> findByFloor(Floor floor, String query, Pageable pageable) {
    return repository.findByFloor(floor, query, pageable);
  }

  public Page<Room> findAllAvailable(LocalDate from, LocalDate to, Pageable pageable) {
    return repository.findAllAvailable(from, to, pageable);
  }
}
