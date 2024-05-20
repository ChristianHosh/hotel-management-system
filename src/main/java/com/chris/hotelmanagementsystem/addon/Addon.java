package com.chris.hotelmanagementsystem.addon;

import com.chris.hotelmanagementsystem.entity.SpecEntity;
import com.chris.hotelmanagementsystem.entity.SpecResponse;
import com.chris.hotelmanagementsystem.entity.annotations.Keyword;
import com.chris.hotelmanagementsystem.entity.annotations.Unique;
import com.chris.hotelmanagementsystem.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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
@Table(name = "t_addon")
public class Addon extends SpecEntity {

  public enum Role {
    ADMIN, USER
  }

  @Column(name = "c_base_price", nullable = false)
  private Double basePrice;

  public Addon.AddonResponse toResponse() {
    return new Addon.AddonResponse(this);
  }

  public static Addon.AddonResponse fromEntity(Addon addon) {
    return addon == null ? null : new Addon.AddonResponse(addon);
  }

  @Getter
  public static class AddonResponse extends SpecResponse {
    private final Double basePrice;

    private AddonResponse(Addon addon) {
      super(addon);
      this.basePrice = addon.getBasePrice();
    }
  }

  public record AddonRequest(
          @NotNull
          @Size(min = 6, max = 40)
          String name,

          @NotNull
          @Size(max = 10000)
          Double basePrice


  ) {

  }
}
