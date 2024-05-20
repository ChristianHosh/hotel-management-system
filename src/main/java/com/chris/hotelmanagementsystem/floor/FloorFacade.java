package com.chris.hotelmanagementsystem.floor;

import com.chris.hotelmanagementsystem.entity.CEntityFacade;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class FloorFacade extends CEntityFacade<Floor> {

  private final FloorRepository repository;

}
