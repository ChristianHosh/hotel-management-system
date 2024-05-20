package com.chris.hotelmanagementsystem.security;

import com.chris.hotelmanagementsystem.user.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/api/auth")
class AuthController {

  private final AuthService service;

  record LoginRequest(String username, String password) {}
  record AuthResponse(String token, User.UserResponse user) {}
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
  ) {}

  @PostMapping("/login")
  public AuthResponse login(@RequestBody @Valid LoginRequest request) {
    return service.login(request);
  }

  @PostMapping("/register")
  public AuthResponse register(@RequestBody @Valid RegisterRequest request) {
    return service.register(request);
  }
}
