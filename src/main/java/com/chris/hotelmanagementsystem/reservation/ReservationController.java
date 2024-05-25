package com.chris.hotelmanagementsystem.reservation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/api/reservations")
class ReservationController {

  private final ReservationService service;

  @GetMapping("/{id}")
  @Operation(
      method = "GET",
      summary = "Get reservation by id",
      description = "Get reservation by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found"),
      }
  )
  public Reservation.ReservationResponse getReservation(@PathVariable Long id) {
    return service.getReservation(id);
  }

  @GetMapping
  @Operation(
      method = "GET",
      summary = "Get all reservations",
      description = "Get all reservations",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found"),
      }
  )
  public Page<Reservation.ReservationResponse> getAllReservations(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "query", defaultValue = "") String query,
      @RequestParam(value = "from", defaultValue = "") String from,
      @RequestParam(value = "to", defaultValue = "") String to
  ) {
    return service.getAllReservations(page, size, query, from, to);
  }

  @PostMapping
  @Operation(
      method = "POST",
      summary = "Create reservation",
      description = "Create reservation",
      responses = {
          @ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "400", description = "Bad Request")
      }
  )
  @ResponseStatus(HttpStatus.CREATED)
  public Reservation.ReservationResponse createReservation(@RequestBody Reservation.ReservationRequest request) {
    return service.createReservation(request);
  }
}
