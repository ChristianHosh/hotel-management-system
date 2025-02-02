package com.chris.hotelmanagementsystem.room_class;

import com.chris.hotelmanagementsystem.entity.CEntityFacade;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class RoomClassFacade extends CEntityFacade<RoomClass> {

  private final RoomClassRepository repository;

}
