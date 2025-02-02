package com.chris.hotelmanagementsystem.floor;

import com.chris.hotelmanagementsystem.entity.CEntity;
import com.chris.hotelmanagementsystem.entity.CEntityResponse;
import com.chris.hotelmanagementsystem.entity.annotations.Unique;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_floor")
public class Floor extends CEntity {

  @Unique
  @Column(name = "c_floor_number", nullable = false)
  private Integer floorNumber;

  public static FloorResponse fromEntity(Floor floor) {
    return floor == null ? null : new FloorResponse(floor);
  }

  public FloorResponse toResponse() {
    return new FloorResponse(this);
  }

  public record FloorRequest(
      @NotNull
      @Min(0)
      Integer floorNumber
  ) {
  }

  @Getter
  public static class FloorResponse extends CEntityResponse {
    private final Integer floorNumber;

    private FloorResponse(Floor floor) {
      super(floor);
      this.floorNumber = floor.getFloorNumber();
    }
  }
}