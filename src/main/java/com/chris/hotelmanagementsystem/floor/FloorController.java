package com.chris.hotelmanagementsystem.floor;

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
@RequestMapping(value = "/api/floors")
class FloorController {

  private final FloorService service;

  @GetMapping
  @Operation(
      method = "GET",
      summary = "Get floors page",
      description = "Get floors page",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
      }
  )
  public Page<Floor.FloorResponse> getFloors(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      @RequestParam(name = "query", defaultValue = "") String query
  ) {
    return service.getFloors(page, size, query);
  }

  @PostMapping
  @Operation(
      method = "POST",
      summary = "Create floor",
      description = "Create floor",
      responses = {
          @ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "400", description = "Bad Request")
      }
  )
  public Floor.FloorResponse createFloor(@RequestBody @Valid Floor.FloorRequest request) {
    return service.createFloor(request);
  }


  @GetMapping("/{id}")
  @Operation(
      method = "GET",
      summary = "Get floor by id",
      description = "Get floor by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public Floor.FloorResponse getFloor(@PathVariable Long id) {
    return service.getFloor(id);
  }

  @PutMapping("/{id}")
  @Operation(
      method = "PUT",
      summary = "Update floor by id",
      description = "Update floor by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public Floor.FloorResponse updateFloor(@PathVariable Long id, @RequestBody @Valid Floor.FloorRequest request) {
    return service.updateFloor(id, request);
  }

  @DeleteMapping("/{id}")
  @Operation(
      method = "DELETE",
      summary = "Delete floor by id",
      description = "Delete floor by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public Floor.FloorResponse deleteFloor(@PathVariable Long id) {
    return service.deleteFloor(id);
  }

}
