package com.chris.hotelmanagementsystem.user;

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
@RequestMapping("/api/users")
class UserController {

  private final UserService service;

  @GetMapping
  @Operation(
      method = "GET",
      summary = "Get users page",
      description = "Get users page",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
      }
  )
  public Page<User.UserResponse> getUsers(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size,
      @RequestParam(name = "query", defaultValue = "") String query
  ) {
    return service.getUsers(page, size, query);
  }

  @PostMapping
  @Operation(
      method = "POST",
      summary = "Create user",
      description = "Create user",
      responses = {
          @ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "400", description = "Bad Request")
      }
  )
  public User.UserResponse createUser(@RequestBody @Valid User.UserRequest request) {
    return service.createUser(request);
  }

  @GetMapping("/{id}")
  @Operation(
      method = "GET",
      summary = "Get user by id",
      description = "Get user by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "401", description = "Unauthorized"),
          @ApiResponse(responseCode = "403", description = "Forbidden"),
          @ApiResponse(responseCode = "404", description = "Not Found")
      }
  )
  public User.UserResponse getUser(@PathVariable Long id) {
    return service.getUser(id);
  }
}
