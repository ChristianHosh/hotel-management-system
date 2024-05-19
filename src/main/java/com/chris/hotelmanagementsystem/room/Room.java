package com.chris.hotelmanagementsystem.room;

import com.chris.hotelmanagementsystem.entity.CEntity;
import com.chris.hotelmanagementsystem.floor.Floor;
import com.chris.hotelmanagementsystem.room_class.RoomClass;
import jakarta.persistence.*;
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

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, optional = false)
  @JoinColumn(name = "c_room_class_id", nullable = false)
  private RoomClass roomClass;

  @Column(name = "c_room_number", nullable = false)
  private Integer roomNumber;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, optional = false)
  @JoinColumn(name = "c_floor_id", nullable = false)
  private Floor floor;

}