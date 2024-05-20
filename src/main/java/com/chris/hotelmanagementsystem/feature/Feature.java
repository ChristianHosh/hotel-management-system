package com.chris.hotelmanagementsystem.feature;

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
@Table(name = "t_feature")
public class Feature extends SpecEntity {

    public enum Role {
        ADMIN, USER
    }

    public Feature.FeatureResponse toResponse() {
        return new Feature.FeatureResponse(this);
    }

    public static Feature.FeatureResponse fromEntity(Feature feature) {
        return feature == null ? null : new Feature.FeatureResponse(feature);
    }

    @Getter
    public static class FeatureResponse extends SpecResponse {
        private final String name;

        private FeatureResponse(Feature feature) {
            super(feature);
            this.name = feature.getName();
        }
    }

    public record FeatureRequest(
            @NotNull
            @Size(min = 6, max = 40)
            String name

    ) {

    }
}
