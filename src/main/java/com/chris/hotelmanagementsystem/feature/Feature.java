package com.chris.hotelmanagementsystem.feature;

import com.chris.hotelmanagementsystem.entity.SpecEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_feature")
public class Feature extends SpecEntity {

}