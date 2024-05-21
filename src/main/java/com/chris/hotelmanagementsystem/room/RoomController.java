package com.chris.hotelmanagementsystem.room;

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
@RequestMapping(value = "/api/rooms")
class RoomController {
  
  private final RoomService service;

  @GetMapping
  @Operation(
      method = "GET",
      summary = "Get rooms page",
      description = "Get rooms page",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
      }
  )
  public Page<Room.RoomResponse> getRooms(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      @RequestParam(name = "query", defaultValue = "") String query
  ) {
    return service.getRooms(page, size, query);
  }

  @PostMapping
  @Operation(
      method = "POST",
      summary = "Create room",
      description = "Create room",
      responses = {
          @ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "400", description = "Bad Request")
      }
  )
  public Room.RoomResponse createRoom(@RequestBody @Valid Room.RoomRequest request) {
    return service.createRoom(request);
  }


  @GetMapping("/{id}")
  @Operation(
      method = "GET",
      summary = "Get room by id",
      description = "Get room by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public Room.RoomResponse getRoom(@PathVariable Long id) {
    return service.getRoom(id);
  }

  @PutMapping("/{id}")
  @Operation(
      method = "PUT",
      summary = "Update room by id",
      description = "Update room by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public Room.RoomResponse updateRoom(@PathVariable Long id, @RequestBody @Valid Room.RoomRequest request) {
    return service.updateRoom(id, request);
  }

  @DeleteMapping("/{id}")
  @Operation(
      method = "DELETE",
      summary = "Delete room by id",
      description = "Delete room by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public Room.RoomResponse deleteRoom(@PathVariable Long id) {
    return service.deleteRoom(id);
  }
}
