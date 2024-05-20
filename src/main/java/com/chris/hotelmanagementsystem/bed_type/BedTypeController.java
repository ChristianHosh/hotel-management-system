package com.chris.hotelmanagementsystem.bed_type;

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
@RequestMapping("/api/bed-types")
class BedTypeController {

  private final BedTypeService service;

  @GetMapping
  @Operation(
      method = "GET",
      summary = "Get bed types page",
      description = "Get bed types page",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
      }
  )
  public Page<BedType.BedTypeResponse> getBedTypes(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      @RequestParam(name = "query", defaultValue = "") String query
  ) {
    return service.getBedTypes(page, size, query);
  }

  @PostMapping
  @Operation(
      method = "POST",
      summary = "Create bed type",
      description = "Create bed type",
      responses = {
          @ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "400", description = "Bad Request")
      }
  )
  public BedType.BedTypeResponse createBedType(@RequestBody @Valid BedType.BedTypeRequest request) {
    return service.createBedType(request);
  }

  @GetMapping("/{id}")
  @Operation(
      method = "GET",
      summary = "Get bed type by id",
      description = "Get bed type by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public BedType.BedTypeResponse getBedType(@PathVariable Long id) {
    return service.getBedType(id);
  }

  @PutMapping("/{id}")
  @Operation(
      method = "PUT",
      summary = "Update bed type by id",
      description = "Update bed type by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public BedType.BedTypeResponse updateBedType(@PathVariable Long id, @RequestBody @Valid BedType.BedTypeRequest request) {
    return service.updateBedType(id, request);
  }

  @DeleteMapping("/{id}")
  @Operation(
      method = "DELETE",
      summary = "Delete bed type by id",
      description = "Delete bed type by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public BedType.BedTypeResponse deleteBedType(@PathVariable Long id) {
    return service.deleteBedType(id);
  }
}
