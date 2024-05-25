package com.chris.hotelmanagementsystem.security;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final UserDetailsService userDetailsService;
  private final AuthEntryPoint authEntryPoint;
  private final AuthFilter authFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    var authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    String hierarchy = "ROLE_ADMIN > ROLE_CUSTOMER";
    roleHierarchy.setHierarchy(hierarchy);
    return roleHierarchy;
  }

  @Bean
  public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
    var adminAuth = AuthorityAuthorizationManager.<RequestAuthorizationContext>hasRole("ADMIN");
    adminAuth.setRoleHierarchy(roleHierarchy());
    var userAuth = AuthorityAuthorizationManager.<RequestAuthorizationContext>hasRole("CUSTOMER");
    userAuth.setRoleHierarchy(roleHierarchy());

    http.cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(e -> e.authenticationEntryPoint(authEntryPoint))
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> configureAuth(auth, adminAuth));

    return configure(http).build();
  }

  private void configureAuth(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth, AuthorityAuthorizationManager<RequestAuthorizationContext> adminAuth) {
    auth
        .requestMatchers("/v3/api-docs/**").permitAll()
        .requestMatchers("/swagger-ui/**").permitAll()

        .requestMatchers(HttpMethod.GET).permitAll()

        .requestMatchers("/api/auth/*").permitAll()
        .requestMatchers("/api/users/*").access(adminAuth)

        // all methods require user to be admin, following line overrides GET method to be accessed by anyone else
        .requestMatchers("/api/addons/*").access(adminAuth)
        .requestMatchers(HttpMethod.GET, "/api/addons/*").permitAll()

        .requestMatchers("/api/room-classes/*").access(adminAuth)
        .requestMatchers(HttpMethod.GET, "/api/room-classes/*").permitAll()

        .requestMatchers("/api/room-classes/beds/*").access(adminAuth)
        .requestMatchers(HttpMethod.GET, "/api/room-classes/beds/*").permitAll()

        .requestMatchers("/api/rooms/*").access(adminAuth)
        .requestMatchers(HttpMethod.GET, "/api/rooms/*").permitAll()

        .requestMatchers(HttpMethod.GET, "/api/reservations/*").access(adminAuth)
        .requestMatchers(HttpMethod.GET, "/api/reservations/{id}").authenticated()
        .requestMatchers(HttpMethod.POST, "/api/reservations").authenticated()


        .requestMatchers("/error").permitAll()
        .anyRequest().authenticated();
  }

  protected HttpSecurity configure(HttpSecurity http) {
    return http;
  }
}
