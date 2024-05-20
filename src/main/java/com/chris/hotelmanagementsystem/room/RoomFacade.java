package com.chris.hotelmanagementsystem.room;

import com.chris.hotelmanagementsystem.entity.CEntityFacade;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class RoomFacade extends CEntityFacade<Room> {

  private final RoomRepository repository;

}
