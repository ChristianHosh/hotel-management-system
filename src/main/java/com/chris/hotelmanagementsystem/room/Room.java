package com.chris.hotelmanagementsystem.room;

import com.chris.hotelmanagementsystem.entity.CEntity;
import com.chris.hotelmanagementsystem.entity.CEntityResponse;
import com.chris.hotelmanagementsystem.entity.annotations.Keyword;
import com.chris.hotelmanagementsystem.floor.Floor;
import com.chris.hotelmanagementsystem.room_class.RoomClass;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_room", uniqueConstraints = {
    @UniqueConstraint(name = "uc_room_c_room_number", columnNames = {"c_room_number", "c_floor_id"})
})
public class Room extends CEntity {

  @Keyword
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, optional = false)
  @JoinColumn(name = "c_room_class_id", nullable = false)
  private RoomClass roomClass;

  @Keyword
  @Column(name = "c_room_number", nullable = false)
  private Integer roomNumber;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, optional = false)
  @JoinColumn(name = "c_floor_id", nullable = false)
  private Floor floor;

  public Double getBasePrice() {
    return roomClass.getBasePrice();
  }

  public RoomResponse toResponse() {
    return new RoomResponse(this);
  }

  public static RoomResponse fromEntity(Room room) {
    return room == null ? null : room.toResponse();
  }

  @Getter
  public static class RoomResponse extends CEntityResponse {
    private final Integer roomNumber;
    private final Floor.FloorResponse floor;
    private final RoomClass.RoomClassResponse roomClass;

    public RoomResponse(Room room) {
      super(room);
      this.roomNumber = room.getRoomNumber();
      this.floor = Floor.fromEntity(room.getFloor());
      this.roomClass = RoomClass.fromEntity(room.getRoomClass());
    }
  }

  record RoomRequest(
      @NotNull
      @Min(0)
      @Max(100)
      Integer roomNumber,

      @NotNull
      Long floorId,

      @NotNull
      Long roomClassId
  ) {
  }
}