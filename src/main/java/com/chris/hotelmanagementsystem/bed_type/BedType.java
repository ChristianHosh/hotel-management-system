package com.chris.hotelmanagementsystem.bed_type;

import com.chris.hotelmanagementsystem.entity.SpecEntity;
import com.chris.hotelmanagementsystem.entity.SpecResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_bed_type")
public class BedType extends SpecEntity {

  public static SpecResponse fromEntity(BedType bedType) {
    return bedType == null ? null : new SpecResponse(bedType);
  }

  public SpecResponse toResponse() {
    return new SpecResponse(this);
  }
}
