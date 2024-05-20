package com.chris.hotelmanagementsystem.security;

import com.chris.hotelmanagementsystem.entity.error.CxException;
import com.chris.hotelmanagementsystem.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class TheBeanMan {

  private TheBeanMan() {

  }

  public static User getCurrentChunkOrNull() {
    var context = SecurityContextHolder.getContext();
    Authentication auth = context.getAuthentication();

    if (auth.getPrincipal() instanceof UserDetailsImpl userDetails)
      return userDetails.getUser();
    return null;
  }

  public static User getCurrentChunk() {
    User chunk = getCurrentChunkOrNull();
    if (chunk == null)
      throw CxException.unauthorized("You are not logged in. Please log in first.");
    return chunk;
  }
}
