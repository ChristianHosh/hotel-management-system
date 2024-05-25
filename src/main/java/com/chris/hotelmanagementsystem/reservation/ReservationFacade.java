package com.chris.hotelmanagementsystem.reservation;

import com.chris.hotelmanagementsystem.entity.CEntityFacade;
import com.chris.hotelmanagementsystem.entity.error.CxException;
import com.chris.hotelmanagementsystem.room.Room;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Service
@RequiredArgsConstructor
public class ReservationFacade extends CEntityFacade<Reservation> {

  private final ReservationRepository repository;

  public boolean isRoomAvailable(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
    Objects.requireNonNull(room, "room cannot be null");
    Objects.requireNonNull(checkInDate, "check-in date cannot be null");
    Objects.requireNonNull(checkOutDate, "check-out date cannot be null");

    if (checkInDate.isAfter(checkOutDate))
      throw CxException.hardcoded(HttpStatus.BAD_REQUEST, "check-in date cannot be after check-out date");

    return repository.isRoomAvailable(room, checkInDate, checkOutDate);
  }

  public Page<Reservation> findAll(int page, int size, String query, String from, String to) {
    LocalDate now = LocalDate.now();
    LocalDate fromDate = now.withDayOfMonth(1);
    LocalDate toDate = now.withDayOfMonth(now.getMonth().length(now.isLeapYear()));

    if (from != null && !from.isBlank())
      fromDate = LocalDate.parse(from);
    if (to != null && !to.isBlank())
      toDate = LocalDate.parse(to);
    return repository.findAll(query, fromDate, toDate, PageRequest.of(page, size));
  }
}
