package com.chris.hotelmanagementsystem.floor;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class FloorService {

  private final FloorFacade floorFacade;

  public Page<Floor.FloorResponse> getFloors(int page, int size, String query) {
    return floorFacade.findAll(query, PageRequest.of(page, size)).map(Floor::toResponse);
  }

  public Floor.FloorResponse createFloor(Floor.FloorRequest request) {
    Floor floor = new Floor();
    floor.setFloorNumber(request.floorNumber());

    return floorFacade.save(floor).toResponse();
  }

  public Floor.FloorResponse updateFloor(Long id, Floor.FloorRequest request) {
    Floor floor = floorFacade.findById(id);
    floor.setFloorNumber(request.floorNumber());

    return floorFacade.save(floor).toResponse();
  }

  public Floor.FloorResponse deleteFloor(Long id) {
    Floor floor = floorFacade.findById(id);

    return floorFacade.delete(floor).toResponse();
  }

  public Floor.FloorResponse getFloor(Long id) {
    return floorFacade.findById(id).toResponse();
  }
}