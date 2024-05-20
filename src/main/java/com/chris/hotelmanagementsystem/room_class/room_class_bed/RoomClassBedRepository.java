package com.chris.hotelmanagementsystem.room_class.room_class_bed;

import com.chris.hotelmanagementsystem.entity.OEntityRepository;
import com.chris.hotelmanagementsystem.room_class.RoomClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

interface RoomClassBedRepository extends OEntityRepository<RoomClassBed> {

  @Query("""
          select rcb from RoomClassBed rcb
          where rcb.roomClass = :roomClass
          and rcb.keyword ilike concat('%', :query, '%')
      """)
  Page<RoomClassBed> findByRoomClass(RoomClass roomClass, String query, Pageable pageable);
}