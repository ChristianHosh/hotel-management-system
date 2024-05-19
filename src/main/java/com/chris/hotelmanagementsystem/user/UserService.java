package com.chris.hotelmanagementsystem.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserService {

  private final UserFacade userFacade;

  public User.UserResponse getUser(Long id) {
    return userFacade.findById(id).toResponse();
  }

  public Page<User.UserResponse> getUsers(int page, int size, String query) {
    return userFacade.findAll(query, PageRequest.of(page, size)).map(User::fromEntity);
  }

  public User.UserResponse createUser(User.UserRequest request) {
    User user = new User();
    user.setName(request.name());
    user.setRole(request.role());
    user.setUsername(request.username());
    user.setPassword(request.password());
    return userFacade.save(user).toResponse();
  }
}
