package com.chris.hotelmanagementsystem.room_class;

import com.chris.hotelmanagementsystem.room_class.room_class_bed.RoomClassBed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/api/room-classes")
class RoomClassController {

  private final RoomClassService service;

  @GetMapping
  @Operation(
      method = "GET",
      summary = "Get room classes page",
      description = "Get room classes page",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
      }
  )
  public Page<RoomClass.RoomClassResponse> getRoomClasses(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      @RequestParam(name = "query", defaultValue = "") String query
  ) {
    return service.getRoomClasses(page, size, query);
  }

  @PostMapping
  @Operation(
      method = "POST",
      summary = "Create room class",
      description = "Create room class",
      responses = {
          @ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "400", description = "Bad Request")
      }
  )
  public RoomClass.RoomClassResponse createRoomClass(@RequestBody @Valid RoomClass.RoomClassRequest request) {
    return service.createRoomClass(request);
  }


  @GetMapping("/{id}")
  @Operation(
      method = "GET",
      summary = "Get room class by id",
      description = "Get room class by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public RoomClass.RoomClassResponse getRoomClass(@PathVariable Long id) {
    return service.getRoomClass(id);
  }

  @PutMapping("/{id}")
  @Operation(
      method = "PUT",
      summary = "Update room class by id",
      description = "Update room class by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public RoomClass.RoomClassResponse updateRoomClass(@PathVariable Long id, @RequestBody @Valid RoomClass.RoomClassRequest request) {
    return service.updateRoomClass(id, request);
  }

  @DeleteMapping("/{id}")
  @Operation(
      method = "DELETE",
      summary = "Delete room class by id",
      description = "Delete room class by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public RoomClass.RoomClassResponse deleteRoomClass(@PathVariable Long id) {
    return service.deleteRoomClass(id);
  }

  @PostMapping("/{id}/beds")
  @Operation(
      method = "POST",
      summary = "Create room class bed",
      description = "Create room class bed for the specified room class",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public RoomClassBed.RoomClassBedResponse createRoomClassBed(
      @PathVariable Long id,
      @RequestBody @Valid RoomClass.RoomClassBedRequest request
  ) {
    return service.createRoomClassBed(id, request);
  }

  @GetMapping("/{id}/beds")
  @Operation(
      method = "GET",
      summary = "Get room class beds",
      description = "Get room class beds for the specified room class",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public Page<RoomClassBed.RoomClassBedResponse> getRoomClassBeds(
      @PathVariable Long id,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      @RequestParam(name = "query", defaultValue = "") String query
  ) {
    return service.getRoomClassBeds(id, page, size, query);
  }

}
