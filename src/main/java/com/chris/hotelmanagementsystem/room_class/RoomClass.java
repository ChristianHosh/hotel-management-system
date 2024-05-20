package com.chris.hotelmanagementsystem.room_class;

import com.chris.hotelmanagementsystem.entity.SpecEntity;
import com.chris.hotelmanagementsystem.entity.SpecResponse;
import com.chris.hotelmanagementsystem.feature.Feature;
import com.chris.hotelmanagementsystem.room_class.room_class_bed.RoomClassBed;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_room_class")
public class RoomClass extends SpecEntity {

  @Column(name = "c_base_price", nullable = false)
  private Double basePrice;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  @JoinTable(name = "t_room_class_features",
      joinColumns = @JoinColumn(name = "c_room_class_id"),
      inverseJoinColumns = @JoinColumn(name = "c_features_id"))
  private Set<Feature> features = new LinkedHashSet<>();

  @OneToMany(mappedBy = "roomClass", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<RoomClassBed> roomClassBeds = new LinkedHashSet<>();

  public Stream<RoomClassBed> getRoomClassBedsStream() {
    return roomClassBeds.stream();
  }

  public static RoomClassResponse fromEntity(RoomClass entity) {
    return entity == null ? null : new RoomClassResponse(entity);
  }

  public RoomClassResponse toResponse() {
    return new RoomClassResponse(this);
  }

  record RoomClassRequest(
      @NotNull
      String name,
      @NotNull
      @Min(0)
      Double basePrice
  ) {
  }

  record RoomClassBedRequest(
      @NotNull
      Long bedTypeId,

      @NotNull
      @Min(0)
      Integer numberOfBeds
  ) {
  }

  @Getter
  public static class RoomClassResponse extends SpecResponse {
    private final Double basePrice;

    public RoomClassResponse(RoomClass entity) {
      super(entity);
      this.basePrice = entity.getBasePrice();
    }
  }
}