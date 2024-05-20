package com.chris.hotelmanagementsystem.bed_type;

import com.chris.hotelmanagementsystem.entity.SpecEntity;
import com.chris.hotelmanagementsystem.entity.SpecResponse;
import com.chris.hotelmanagementsystem.entity.annotations.Keyword;
import com.chris.hotelmanagementsystem.entity.annotations.Unique;
import com.chris.hotelmanagementsystem.user.User;
import jakarta.persistence.Column;
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

    public enum Role {
        ADMIN, USER
    }


    public BedType.BedTypeResponse toResponse() {
        return new BedType.BedTypeResponse(this);
    }

    public static BedType.BedTypeResponse fromEntity(BedType bedType) {
        return bedType == null ? null : new BedType.BedTypeResponse(bedType);
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
