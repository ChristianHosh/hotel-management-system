package com.chris.hotelmanagementsystem.room;

import com.chris.hotelmanagementsystem.floor.FloorFacade;
import com.chris.hotelmanagementsystem.room_class.RoomClassFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
}
