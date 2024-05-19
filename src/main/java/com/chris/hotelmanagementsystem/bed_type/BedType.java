package com.chris.hotelmanagementsystem.bed_type;

import com.chris.hotelmanagementsystem.entity.SpecEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_bed_type")
public class BedType extends SpecEntity {

}