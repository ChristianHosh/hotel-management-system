package com.chris.hotelmanagementsystem.room_class;

import com.chris.hotelmanagementsystem.bed_type.BedType;
import com.chris.hotelmanagementsystem.bed_type.BedTypeFacade;
import com.chris.hotelmanagementsystem.room_class.room_class_bed.RoomClassBed;
import com.chris.hotelmanagementsystem.room_class.room_class_bed.RoomClassBedFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RoomClassService {

  private final RoomClassFacade roomClassFacade;
  private final BedTypeFacade bedTypeFacade;
  private final RoomClassBedFacade roomClassBedFacade;

  public Page<RoomClass.RoomClassResponse> getRoomClasses(int page, int size, String query) {
    return roomClassFacade.findAll(query, PageRequest.of(page, size)).map(RoomClass::toResponse);
  }

  public RoomClass.RoomClassResponse createRoomClass(RoomClass.RoomClassRequest request) {
    RoomClass roomClass = new RoomClass();
    roomClass.setName(request.name());
    roomClass.setBasePrice(request.basePrice());

    return roomClassFacade.save(roomClass).toResponse();
  }

  public RoomClass.RoomClassResponse getRoomClass(Long id) {
    return roomClassFacade.findById(id).toResponse();
  }

  public RoomClass.RoomClassResponse updateRoomClass(Long id, RoomClass.RoomClassRequest request) {
      RoomClass roomClass = roomClassFacade.findById(id);
      roomClass.setName(request.name());
      roomClass.setBasePrice(request.basePrice());

      return roomClassFacade.save(roomClass).toResponse();
  }

  public RoomClass.RoomClassResponse deleteRoomClass(Long id) {
    RoomClass roomClass = roomClassFacade.findById(id);

    return roomClassFacade.delete(roomClass).toResponse();
  }

  public RoomClassBed.RoomClassBedResponse createRoomClassBed(Long id, RoomClass.RoomClassBedRequest request) {
    RoomClass roomClass = roomClassFacade.findById(id);
    BedType bedType = bedTypeFacade.findById(request.bedTypeId());

    RoomClassBed roomClassBed = new RoomClassBed();
    roomClassBed.setRoomClass(roomClass);
    roomClassBed.setBedType(bedType);
    roomClassBed.setNumberOfBeds(request.numberOfBeds());

    roomClassFacade.save(roomClass);
    return roomClassBed.toResponse();
  }

  public Page<RoomClassBed.RoomClassBedResponse> getRoomClassBeds(Long id, int page, int size, String query) {
    RoomClass roomClass = roomClassFacade.findById(id);

    return roomClassBedFacade.findByRoomClass(roomClass, query, PageRequest.of(page, size))
        .map(RoomClassBed::toResponse);
  }
}
