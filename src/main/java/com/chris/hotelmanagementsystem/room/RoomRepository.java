package com.chris.hotelmanagementsystem.room;

import com.chris.hotelmanagementsystem.entity.OEntityRepository;
import com.chris.hotelmanagementsystem.floor.Floor;
import com.chris.hotelmanagementsystem.room_class.RoomClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

interface RoomRepository extends OEntityRepository<Room> {

  @Query("""
      select r from Room r
      where r.roomClass = :roomClass
      and r.keyword ilike concat('%', :query, '%')
      """)
  Page<Room> findByRoomClass(RoomClass roomClass, String query, Pageable pageable);

  @Query("""
       select r from Room r
       where r.floor = :floor
       and r.keyword ilike concat('%', :query, '%')
      """)
  Page<Room> findByFloor(Floor floor, String query, Pageable pageable);

  @Query("""
      select r from Room r
      inner join Reservation res on r.id in elements(res.rooms)
      where (res.checkInDate not between :from and :to)
      and (res.checkOutDate not between :from and :to)
      group by r.id
      """)
  Page<Room> findAllAvailable(LocalDate from, LocalDate to, Pageable pageable);
}