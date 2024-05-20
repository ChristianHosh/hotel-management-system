package com.chris.hotelmanagementsystem.user;

import com.chris.hotelmanagementsystem.entity.CEntityFacade;
import com.chris.hotelmanagementsystem.entity.error.CxException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class UserFacade extends CEntityFacade<User> {

  private final UserRepository repository;

  public User findByUsername(String username) {
    return repository.findByUsername(username)
        .orElseThrow(() -> CxException.notFound(entityClass(), "username", username));
  }
}
