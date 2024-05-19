package com.chris.hotelmanagementsystem.addon;

import com.chris.hotelmanagementsystem.entity.SpecEntity;
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
@Table(name = "t_addon")
public class Addon extends SpecEntity {

  @Column(name = "c_base_price", nullable = false)
  private Double basePrice;
}