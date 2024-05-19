package com.chris.hotelmanagementsystem.room_class;

import com.chris.hotelmanagementsystem.entity.SpecEntity;
import com.chris.hotelmanagementsystem.feature.Feature;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

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



}