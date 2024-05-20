package com.chris.hotelmanagementsystem.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SpecRequest(

    @NotNull
    @Size(max = 255)
    String name
) {
}
