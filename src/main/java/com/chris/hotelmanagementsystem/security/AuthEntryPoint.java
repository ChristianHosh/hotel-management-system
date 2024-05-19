package com.chris.hotelmanagementsystem.security;

import com.chris.hotelmanagementsystem.entity.error.ApiError;
import com.chris.hotelmanagementsystem.entity.error.CxException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
    log.error("Unauthorized error: {}", authException.getMessage(), authException);

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), ApiError.from(CxException.forbidden(authException.getMessage())));
  }
}
