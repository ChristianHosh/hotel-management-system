package com.chris.hotelmanagementsystem.security;

import com.chris.hotelmanagementsystem.entity.error.CxException;
import com.chris.hotelmanagementsystem.user.User;
import com.chris.hotelmanagementsystem.user.UserFacade;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AuthService {

  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final UserFacade userFacade;
  private final JwtUtils jwtUtils;

  public AuthController.AuthResponse login(AuthController.LoginRequest request) {
    try {
      var user = userFacade.findByUsername(request.username());
      var token = new UsernamePasswordAuthenticationToken(user.getUsername(), request.password());
      var auth = authenticationManager.authenticate(token);

      var jwt = jwtUtils.generateJwtToken(auth);

      return new AuthController.AuthResponse(jwt, user.toResponse());
    } catch (CxException e) {
      throw CxException.hardcoded(HttpStatus.UNAUTHORIZED, "bad credentials");
    } catch (Exception e) {
      throw CxException.unexpected(e);
    }
  }

  @Transactional
  public AuthController.AuthResponse register(AuthController.RegisterRequest request) {
    var user = new User();
    user.setName(request.name());
    user.setUsername(request.username());
    user.setPassword(passwordEncoder.encode(request.password()));
    user.setRole(User.Role.CUSTOMER);

    userFacade.save(user);

    return login(new AuthController.LoginRequest(request.username(), request.password()));
  }
}
