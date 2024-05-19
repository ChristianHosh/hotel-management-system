package com.chris.hotelmanagementsystem.user;

import com.chris.hotelmanagementsystem.entity.SpecEntity;
import com.chris.hotelmanagementsystem.entity.SpecEntityResponse;
import com.chris.hotelmanagementsystem.entity.annotations.Keyword;
import com.chris.hotelmanagementsystem.entity.annotations.Unique;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_user")
public class User extends SpecEntity {
  
  public enum Role {
    ADMIN, USER
  }

  @Unique
  @Keyword
  @Column(name = "c_username", nullable = false, length = 40)
  private String username;

  @Column(name = "c_password", nullable = false, length = 120)
  private String password;

  @Enumerated
  @Column(name = "c_role", nullable = false)
  private Role role;

  public UserResponse toResponse() {
    return new UserResponse(this);
  }

  public static UserResponse fromEntity(User user) {
    return user == null ? null : new UserResponse(user);
  }

  @Getter
  public static class UserResponse extends SpecEntityResponse {
    private final String role;
    private final String username;

    private UserResponse(User user) {
      super(user);
      this.username = user.getUsername();
      this.role = user.getRole().name().toLowerCase();
    }
  }
  
  public record UserRequest(
      @NotNull
      @Size(min = 6, max = 40)
      String name,

      @NotNull
      @Size(min = 6, max = 40)
      String username,

      @NotNull
      @Size(min = 6, max = 40)
      String password,
      
      @NotNull
      Role role
  ) {
    
  }
}