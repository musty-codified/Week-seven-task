package com.week8.activitytrackerappws.repository;

import com.week8.activitytrackerappws.model.TaskEntity;
import com.week8.activitytrackerappws.model.UserEntity;
import com.week8.activitytrackerappws.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findByUserEntity(UserEntity userEntity);
    List<TaskEntity> findByStatusAndUserEntity(Status status, UserEntity userEntity);

    Optional<TaskEntity> findByIdAndUserEntity(Long taskId, UserEntity userEntity);
}
