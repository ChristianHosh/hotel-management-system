package com.chris.hotelmanagementsystem.user;

import com.chris.hotelmanagementsystem.entity.CEntityFacade;
import com.chris.hotelmanagementsystem.entity.CEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade extends CEntityFacade<User> {
  
  private final UserRepository userRepository;
  @Override
  public CEntityRepository<User> repository() {
    return userRepository;
  }

  @Override
  public Class<User> entityClass() {
    return User.class;
  }
}
