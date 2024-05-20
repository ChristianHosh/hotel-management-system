package com.chris.hotelmanagementsystem.feature;

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
@RequestMapping(value = "/api/features", consumes = "application/json", produces = "application/json")
class FeatureController {

  private final FeatureService service;

  @GetMapping
  @Operation(
      method = "GET",
      summary = "Get features page",
      description = "Get features page",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
      }
  )
  public Page<Feature.FeatureResponse> getFeatures(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      @RequestParam(name = "query", defaultValue = "") String query
  ) {
    return service.getFeatures(page, size, query);
  }

  @PostMapping
  @Operation(
      method = "POST",
      summary = "Create feature",
      description = "Create feature",
      responses = {
          @ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "400", description = "Bad Request")
      }
  )
  public Feature.FeatureResponse createFeature(@RequestBody @Valid Feature.FeatureRequest request) {
    return service.createFeature(request);
  }

  @GetMapping("/{id}")
  @Operation(
      method = "GET",
      summary = "Get feature by id",
      description = "Get feature by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public Feature.FeatureResponse getFeature(@PathVariable Long id) {
    return service.getFeature(id);
  }

  @PutMapping("/{id}")
  @Operation(
      method = "PUT",
      summary = "Update feature by id",
      description = "Update feature by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public Feature.FeatureResponse updateFeature(@PathVariable Long id, @RequestBody @Valid Feature.FeatureRequest request) {
    return service.updateFeature(id, request);
  }

  @DeleteMapping("/{id}")
  @Operation(
      method = "DELETE",
      summary = "Delete feature by id",
      description = "Delete feature by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public Feature.FeatureResponse deleteFeature(@PathVariable Long id) {
    return service.deleteFeature(id);
  }
}
