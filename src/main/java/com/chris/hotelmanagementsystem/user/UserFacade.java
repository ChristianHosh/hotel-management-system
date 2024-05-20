package com.chris.hotelmanagementsystem.user;

import com.chris.hotelmanagementsystem.entity.CEntityFacade;
import com.chris.hotelmanagementsystem.entity.OEntityRepository;
import com.chris.hotelmanagementsystem.entity.error.CxException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade extends CEntityFacade<User> {

  private final UserRepository repository;

  @Override
  public OEntityRepository<User> repository() {
    return repository;
  }

  @Override
  public Class<User> entityClass() {
    return User.class;
  }

  public User findByUsername(String username) {
    return repository.findByUsername(username)
        .orElseThrow(() -> CxException.notFound(entityClass(), "username", username));
  }
}
