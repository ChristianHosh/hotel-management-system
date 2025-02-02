package com.chris.hotelmanagementsystem.security;

import com.chris.hotelmanagementsystem.entity.error.ApiError;
import com.chris.hotelmanagementsystem.entity.error.CxException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
    try {
      String jwt = parseJwt(request);
      if (jwtUtils.validateJwtToken(jwt)) {
        String username = jwtUtils.getUsernameFromJwtToken(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        var authenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    } catch (Exception e) {
      ApiError error = ApiError.from(CxException.unexpected(e));
      writeError(response, error);
      return;
    }

    filterChain.doFilter(request, response);
  }

  private void writeError(HttpServletResponse response, ApiError error) throws IOException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(error.status());

    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), error);
  }

  private String parseJwt(HttpServletRequest request) {
    String auth = request.getHeader("Authorization");
    if (StringUtils.hasText(auth) && auth.startsWith("Bearer ")) {
      return auth.substring(7);
    }

    return null;
  }
}
