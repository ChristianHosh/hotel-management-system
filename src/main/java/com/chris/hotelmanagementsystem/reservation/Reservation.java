package com.chris.hotelmanagementsystem.reservation;

import com.chris.hotelmanagementsystem.addon.Addon;
import com.chris.hotelmanagementsystem.entity.CEntity;
import com.chris.hotelmanagementsystem.room.Room;
import com.chris.hotelmanagementsystem.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_reservation")
public class Reservation extends CEntity {

  enum PaymentStatus {
    PENDING_PAYMENT, PAID, CANCELED
  }

  @Column(name = "c_number_of_adults", nullable = false)
  private Integer numberOfAdults;

  @Column(name = "c_number_of_children", nullable = false)
  private Integer numberOfChildren;

  @Column(name = "c_check_in_date", nullable = false)
  private LocalDate checkInDate;

  @Column(name = "c_check_out_date")
  private LocalDate checkOutDate;

  @Column(name = "c_total_price", nullable = false)
  private Double totalPrice;

  @Enumerated
  @Column(name = "c_payment_status", nullable = false)
  private PaymentStatus paymentStatus;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, optional = false)
  @JoinColumn(name = "c_guest_id", nullable = false)
  private User guest;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  @JoinTable(name = "t_reservation_rooms",
          joinColumns = @JoinColumn(name = "c_reservation_id"),
          inverseJoinColumns = @JoinColumn(name = "c_room_id"))
  private Set<Room> rooms = new LinkedHashSet<>();

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  @JoinTable(name = "t_reservation_addons",
          joinColumns = @JoinColumn(name = "c_reservation_id"),
          inverseJoinColumns = @JoinColumn(name = "c_addon_id"))
  private Set<Addon> addons = new LinkedHashSet<>();


}