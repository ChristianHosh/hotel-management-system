package com.chris.hotelmanagementsystem.bed_type;

import com.chris.hotelmanagementsystem.entity.SpecEntity;
import com.chris.hotelmanagementsystem.entity.SpecResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

  public enum Role {
    ADMIN, USER
  }

  @Getter
  public static class BedTypeResponse extends SpecResponse {
    private final String name;

    private BedTypeResponse(BedType bedType) {
      super(bedType);
      this.name = bedType.getName();
    }
  }

  public record BedTypeRequest(
      @NotNull
      @Size(min = 6, max = 40)
      String name
  ) {

  }
}
