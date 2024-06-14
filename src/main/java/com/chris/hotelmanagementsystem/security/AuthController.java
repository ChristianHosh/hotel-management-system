package com.chris.hotelmanagementsystem.security;

import com.chris.hotelmanagementsystem.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/api/auth")
class AuthController {

  private final AuthService service;

    @PostMapping("/login")
    @Operation(
            method = "POST",
            summary = "User login",
            description = "Authenticate a user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    public AuthResponse login(@RequestBody @Valid LoginRequest request) {
        return service.login(request);
    }

    @PostMapping("/register")
    @Operation(
            method = "POST",
            summary = "User registration",
            description = "Register a new user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Registration successful", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    public AuthResponse register(@RequestBody @Valid RegisterRequest request) {
        return service.register(request);
    }


  record LoginRequest(String username, String password) {
  }

  record AuthResponse(String token, User.UserResponse user) {
  }

  record RegisterRequest(
      @NotNull
      @Size(min = 6, max = 40)
      String name,

      @NotNull
      @Size(min = 6, max = 40)
      String username,

      @NotNull
      @Size(min = 6, max = 40)
      String password
  ) {
  }
}
