package com.chris.hotelmanagementsystem.reservation;

import com.chris.hotelmanagementsystem.addon.Addon;
import com.chris.hotelmanagementsystem.addon.AddonFacade;
import com.chris.hotelmanagementsystem.entity.error.CxException;
import com.chris.hotelmanagementsystem.room.Room;
import com.chris.hotelmanagementsystem.room.RoomFacade;
import com.chris.hotelmanagementsystem.security.TheBeanMan;
import com.chris.hotelmanagementsystem.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
class ReservationService {

  private final ReservationFacade reservationFacade;
  private final RoomFacade roomFacade;
  private final AddonFacade addonFacade;

  public Reservation.ReservationResponse getReservation(Long id) {
    User currentUser = TheBeanMan.getCurrentChunk();
    Reservation reservation = reservationFacade.findById(id);

    // only admin or reservation guest can view the reservation
    if (currentUser.getRole() == User.Role.ADMIN || Objects.equals(reservation.getGuest(), currentUser))
      return reservation.toResponse();
    else
      throw CxException.forbidden("you can't view this reservation");
  }

  public Reservation.ReservationResponse createReservation(Reservation.ReservationRequest request) {
    User currentUser = TheBeanMan.getCurrentChunk();

    Reservation reservation = new Reservation();

    LocalDate checkInDate = request.checkInDate();
    LocalDate checkOutDate = request.checkOutDate();

    if (checkInDate.isAfter(checkOutDate))
      throw CxException.badRequest(reservation, "check-in date cannot be after check-out date");

    // add rooms to reservation, also check room availability
    for (Room room : roomFacade.findAllById(request.roomIds())) {
      if (reservationFacade.isRoomAvailable(room, checkInDate, checkOutDate)) {
        reservation.getRooms().add(room);
      } else {
        throw CxException.badRequest(reservation, "room (" + room.getId() + ") is not available between [" + checkInDate + "] and [" + checkOutDate + "]");
      }
    }

    // add addons to reservation
    for (Addon addon : addonFacade.findAllById(request.addonIds())) {
      reservation.getAddons().add(addon);
    }

    reservation.setCheckInDate(checkInDate);
    reservation.setCheckOutDate(checkOutDate);
    reservation.setGuest(currentUser);
    reservation.setNumberOfAdults(request.numberOfAdults());
    reservation.setNumberOfChildren(request.numberOfChildren());
    reservation.setPaymentStatus(Reservation.PaymentStatus.PENDING_PAYMENT);

    return reservationFacade.save(reservation).toResponse();
  }

  public Page<Reservation.ReservationResponse> getAllReservations(int page, int size, String query, String from, String to) {
    return reservationFacade.findAll(page, size, query, from, to)
        .map(Reservation::toResponse);
  }

  public Reservation.ReservationResponse cancelReservation(Long id) {
    Reservation reservation = reservationFacade.findById(id);
    if (reservation.getReservationStatus() != Reservation.ReservationStatus.CONFIRMED)
      throw CxException.badRequest(reservation, "only confirmed reservations can be cancelled");

    reservation.setReservationStatus(Reservation.ReservationStatus.CANCELED);

    return reservationFacade.save(reservation).toResponse();
  }

  public Reservation.ReservationResponse checkIn(Long id) {
    Reservation reservation = reservationFacade.findById(id);
    if (reservation.getReservationStatus() != Reservation.ReservationStatus.CONFIRMED)
      throw CxException.badRequest(reservation, "only confirmed reservation can be checked in");
    if (reservation.getCheckInDate().isBefore(LocalDate.now().plusDays(1)))
      throw CxException.badRequest(reservation, "check-in date hasn't passed yet");

    reservation.setReservationStatus(Reservation.ReservationStatus.CHECKED_IN);

    return reservationFacade.save(reservation).toResponse();
  }

  public Reservation.ReservationResponse checkOut(Long id) {
    Reservation reservation = reservationFacade.findById(id);
    if (reservation.getReservationStatus() != Reservation.ReservationStatus.CHECKED_IN)
      throw CxException.badRequest(reservation, "only checked-in reservation can be checked out");
    if (reservation.getCheckOutDate().isBefore(LocalDate.now().plusDays(1)))
      throw CxException.badRequest(reservation, "check-out date hasn't passed yet");

    reservation.setReservationStatus(Reservation.ReservationStatus.CHECKED_OUT);

    return reservationFacade.save(reservation).toResponse();
  }
}
