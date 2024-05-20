package com.chris.hotelmanagementsystem.security;

import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebCorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(@NotNull CorsRegistry cors) {
    cors.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("*")
        .maxAge(3600L)
        .allowedHeaders("*")
        .allowCredentials(false);
  }
}
