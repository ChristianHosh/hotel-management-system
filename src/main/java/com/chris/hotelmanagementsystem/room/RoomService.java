package com.chris.hotelmanagementsystem.room;

import com.chris.hotelmanagementsystem.entity.error.CxException;
import com.chris.hotelmanagementsystem.floor.FloorFacade;
import com.chris.hotelmanagementsystem.room_class.RoomClassFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
class RoomService {

  private final RoomFacade roomFacade;
  private final RoomClassFacade roomClassFacade;
  private final FloorFacade floorFacade;


  public Page<Room.RoomResponse> getRooms(int page, int size, String query) {
    return roomFacade.findAll(query, PageRequest.of(page, size))
        .map(Room::toResponse);
  }

  public Room.RoomResponse createRoom(Room.RoomRequest request) {
    Room room = new Room();
    room.setRoomNumber(request.roomNumber());
    room.setFloor(floorFacade.findById(request.floorId()));
    room.setRoomClass(roomClassFacade.findById(request.roomClassId()));

    return roomFacade.save(room).toResponse();
  }

  public Room.RoomResponse getRoom(Long id) {
    return roomFacade.findById(id).toResponse();
  }

  public Room.RoomResponse updateRoom(Long id, Room.RoomRequest request) {
    Room room = roomFacade.findById(id);
    room.setRoomNumber(request.roomNumber());
    room.setFloor(floorFacade.findById(request.floorId()));
    room.setRoomClass(roomClassFacade.findById(request.roomClassId()));

    return roomFacade.save(room).toResponse();
  }

  public Room.RoomResponse deleteRoom(Long id) {
    Room room = roomFacade.findById(id);

    return roomFacade.delete(room).toResponse();
  }

  public Page<Room.RoomResponse> getAvailableRooms(int page, int size, String fromDate, String toDate) {

    LocalDate from;
    LocalDate to;
    try {
      LocalDate now = LocalDate.now();
      if (fromDate.isBlank())
        from = now.withDayOfMonth(1);
      else
        from = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

      if (toDate.isBlank())
        to = now.withDayOfMonth(now.getMonth().length(now.isLeapYear()));
      else
        to = LocalDate.parse(toDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    } catch (DateTimeException e) {
      throw CxException.badRequest("Invalid date format");
    }

    return roomFacade.findAllAvailable(from, to, PageRequest.of(page, size))
        .map(Room::toResponse);

  }
}
