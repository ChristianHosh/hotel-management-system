package com.chris.hotelmanagementsystem.user;

import com.chris.hotelmanagementsystem.entity.OEntityRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface UserRepository extends OEntityRepository<User> {

  @Query("select u from User u where u.username = :username")
  Optional<User> findByUsername(String username);
}