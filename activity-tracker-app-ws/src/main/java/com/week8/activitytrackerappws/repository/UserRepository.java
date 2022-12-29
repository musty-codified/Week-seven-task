package com.week8.activitytrackerappws.repository;


import com.week8.activitytrackerappws.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
  Optional<UserEntity>  findUserByEmail(String email);
}
