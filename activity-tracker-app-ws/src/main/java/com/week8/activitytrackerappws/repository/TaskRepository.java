package com.week8.activitytrackerappws.repository;

import com.week8.activitytrackerappws.entitiy.TaskEntity;
import com.week8.activitytrackerappws.entitiy.UserEntity;
import com.week8.activitytrackerappws.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findTasksByUser(UserEntity userEntity);
    List<TaskEntity> findTaskByStatusAndStudent(Status status, UserEntity userEntity);

    Optional<TaskEntity> findTaskByIdAndStudent(Long taskId, UserEntity userEntity);
}
