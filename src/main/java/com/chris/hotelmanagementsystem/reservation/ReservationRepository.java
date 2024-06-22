package com.chris.hotelmanagementsystem.reservation;

import com.chris.hotelmanagementsystem.entity.OEntityRepository;
import com.chris.hotelmanagementsystem.room.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface ReservationRepository extends OEntityRepository<Reservation> {

  @Query("""
      select (count(res.id)) from Reservation res
      where (
                (:checkInDate between res.checkInDate and res.checkOutDate) or
                (:checkOutDate between res.checkInDate and res.checkOutDate) or
                (res.checkInDate between :checkInDate and :checkOutDate)
            ) and
            (:room in elements(res.rooms))
      """)
  int isRoomAvailable(Room room, LocalDate checkInDate, LocalDate checkOutDate);

  @Query("""
      select res from Reservation res
      where (res.keyword ilike concat('%', :query, '%')) and
            (res.checkInDate between :fromDate and :toDate)
      """)
  Page<Reservation> findAll(String query, LocalDate fromDate, LocalDate toDate, Pageable pageable);
}