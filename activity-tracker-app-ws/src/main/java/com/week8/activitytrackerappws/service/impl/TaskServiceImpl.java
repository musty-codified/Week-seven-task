package com.week8.activitytrackerappws.service.impl;

import com.week8.activitytrackerappws.dto.TaskDto;
import com.week8.activitytrackerappws.exception.ErrorMessages;
import com.week8.activitytrackerappws.model.TaskEntity;
import com.week8.activitytrackerappws.model.UserEntity;
import com.week8.activitytrackerappws.enums.Status;
import com.week8.activitytrackerappws.exception.UserServiceException;
import com.week8.activitytrackerappws.exception.BadRequestException;
import com.week8.activitytrackerappws.repository.TaskRepository;
import com.week8.activitytrackerappws.repository.UserRepository;
import com.week8.activitytrackerappws.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.week8.activitytrackerappws.enums.Status.PENDING;

@Service
@RequiredArgsConstructor

public class TaskServiceImpl implements TaskService {
//    @Autowired
   private final TaskRepository taskRepository;

//    @Autowired
   private final UserRepository userRepository;
    @Override
    public TaskEntity createTask(Long userId, TaskDto taskDto) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(taskDto.getTitle());
        taskEntity.setDescription(taskDto.getDescription());
        taskEntity.setStatus(PENDING);
        taskEntity.setCreatedAt(LocalDateTime.now());
        taskEntity.setUserEntity(userEntity);
        return taskRepository.save(taskEntity);
    }


    @Override
    public List<TaskDto> getAllTasks(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).
                orElseThrow(()->new BadRequestException((ErrorMessages.NO_RECORD_FOUND.getErrorMessage())));
        List<TaskEntity> tasks = taskRepository.findByUserEntity(userEntity);
        List<TaskDto> taskDtoList = new ArrayList<>();

        tasks.forEach(task -> {
            System.out.println("------>" + task);
            TaskDto taskDto = new TaskDto();
            BeanUtils.copyProperties(task, taskDto);
            taskDtoList.add(taskDto);
    });
        return taskDtoList;
}

    @Override
    public TaskDto getTask(Long studentId, Long taskId) {
        UserEntity userEntity = userRepository.findById(studentId)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));

        TaskEntity taskEntity = taskRepository.findByIdAndUserEntity(taskId, userEntity)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));

        TaskDto taskDto = new TaskDto();
        BeanUtils.copyProperties(taskEntity, taskDto);

        return taskDto;
    }

    @Override
    public List<TaskDto> getTasksByStatus(Long userId, String status) {
        Status statusCheck = checkStatus(status);

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));

        List<TaskEntity> tasks = taskRepository.findByStatusAndUserEntity(statusCheck, userEntity);

        List<TaskDto> taskDtoList = new ArrayList<>();

        tasks.forEach(task -> {
            TaskDto taskDto = new TaskDto();
            BeanUtils.copyProperties(task, taskDto);
            taskDtoList.add(taskDto);
        });

        return taskDtoList;

    }

    @Override
    public TaskDto updateTaskStatus(Long userId, Long taskId, String status) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));

        TaskEntity taskEntity = taskRepository.findByIdAndUserEntity(taskId, userEntity)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));

        Status statusCheck = checkStatus(status);

        if (!taskEntity.getStatus().equals(statusCheck)) {
            taskEntity.setStatus(statusCheck);
            if (statusCheck.equals(Status.DONE)) {
                taskEntity.setCompletedAt(LocalDateTime.now());
                taskEntity.setUpdatedAt(LocalDateTime.now());
            } else {
                taskEntity.setUpdatedAt(LocalDateTime.now());
                taskEntity.setCompletedAt(null);
            }
        }

            TaskDto taskDto = new TaskDto();
            BeanUtils.copyProperties(taskEntity, taskDto);

            return taskDto;

    }

    @Override
    @Transactional
    public TaskDto updateTask(Long userId, Long taskId, String title, String description) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));

        TaskEntity taskEntity = taskRepository.findByIdAndUserEntity(taskId, userEntity)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));

        System.out.println("title->" + title);

        if(title != null && title.length() > 0 && !title.equals(taskEntity.getTitle())){
            System.out.println("updating task");
            taskEntity.setTitle(title);
            taskEntity.setUpdatedAt(LocalDateTime.now());
        }

        if(description != null && description.length() > 0 && !description.equals(taskEntity.getDescription())){
            System.out.println("updating desc");
            taskEntity.setDescription(description);
            taskEntity.setUpdatedAt( LocalDateTime.now());
        }
        System.out.println(taskEntity);
        TaskDto taskDto = new TaskDto();
        BeanUtils.copyProperties(taskEntity, taskDto);

        System.out.println(taskDto);
        return taskDto;
    }

    @Override
    public void deleteTask(Long userId, Long taskId) {
            UserEntity userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new BadRequestException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));

            taskRepository.findByIdAndUserEntity(taskId, userEntity)
                    .ifPresentOrElse(
                            taskRepository::delete,
                            () -> {
                                throw new BadRequestException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
                            });

        }

        private Status checkStatus(String status) {
            Status statusCheck;

            if(status.equalsIgnoreCase("pending")){
                statusCheck = PENDING;
            } else if (status.equalsIgnoreCase("in_progress")) {
                statusCheck = Status.IN_PROGRESS;
            } else if (status.equalsIgnoreCase("done")){
                statusCheck = Status.DONE;
            } else {
                throw new UserServiceException("Invalid Status");
            }

            return statusCheck;
        }

        }


