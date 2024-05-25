package com.chris.hotelmanagementsystem.user;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreator {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;

  @EventListener(ApplicationReadyEvent.class)
  public void onApplicationEvent() {
    var optional = repository.findByUsername("admin");

    if (optional.isEmpty()) {
      User user = new User();
      user.setUsername("admin");
      user.setName("admin");
      user.setPassword(passwordEncoder.encode("a12345"));
      user.setRole(User.Role.ADMIN);
      repository.save(user);
    }
  }

}
