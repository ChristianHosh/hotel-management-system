package com.chris.hotelmanagementsystem.floor;

import com.chris.hotelmanagementsystem.entity.CEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_floor")
public class Floor extends CEntity {

  @Column(name = "c_floor_number", nullable = false)
  private Integer floorNumber;

}