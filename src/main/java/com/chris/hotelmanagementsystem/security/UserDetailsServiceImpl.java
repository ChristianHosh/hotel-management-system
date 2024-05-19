package com.chris.hotelmanagementsystem.security;

import com.chris.hotelmanagementsystem.entity.error.CxException;
import com.chris.hotelmanagementsystem.user.User;
import com.chris.hotelmanagementsystem.user.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserDetailsServiceImpl implements UserDetailsService {

  private final UserFacade userFacade;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      User user = userFacade.findByUsername(username);

      return new UserDetailsImpl(user);
    } catch (CxException e) {
      throw new UsernameNotFoundException(e.getMessage());
    }
  }
}
