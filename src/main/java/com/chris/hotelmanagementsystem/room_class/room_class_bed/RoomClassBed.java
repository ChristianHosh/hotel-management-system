package com.chris.hotelmanagementsystem.room_class.room_class_bed;

import com.chris.hotelmanagementsystem.bed_type.BedType;
import com.chris.hotelmanagementsystem.room_class.RoomClass;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_room_class_bed")
public class RoomClassBed implements Serializable {

  @EmbeddedId
  private RoomClassBedId id;

  @MapsId("roomClassId")
  @ManyToOne(optional = false)
  @JoinColumn(name = "c_room_class_id", nullable = false)
  private RoomClass roomClass;

  @MapsId("bedTypeId")
  @ManyToOne(optional = false)
  @JoinColumn(name = "c_bed_type_id", nullable = false)
  private BedType bedType;

  @Column(name = "c_number_of_beds", nullable = false)
  private Integer numberOfBeds;

  @Override
  public final boolean equals(Object object) {
    if (this == object) return true;
    if (!(object instanceof RoomClassBed that)) return false;

    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  public record RoomClassBedId(
          @Column(name = "c_room_class_id", nullable = false)
          Long roomClassId,
          @Column(name = "c_bed_type_id", nullable = false)
          Long bedTypeId
  ) implements Serializable {
    @Override
    public boolean equals(Object object) {
      if (this == object) return true;
      if (!(object instanceof RoomClassBedId that)) return false;

      return bedTypeId.equals(that.bedTypeId) && roomClassId.equals(that.roomClassId);
    }

    @Override
    public int hashCode() {
      int result = roomClassId.hashCode();
      result = 31 * result + bedTypeId.hashCode();
      return result;
    }
  }
}