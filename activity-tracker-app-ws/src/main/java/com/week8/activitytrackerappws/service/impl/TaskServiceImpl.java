package com.week8.activitytrackerappws.service.impl;

import com.week8.activitytrackerappws.dto.TaskDto;
import com.week8.activitytrackerappws.entitiy.TaskEntity;
import com.week8.activitytrackerappws.entitiy.UserEntity;
import com.week8.activitytrackerappws.enums.Status;
import com.week8.activitytrackerappws.exception.BadRequestException;
import com.week8.activitytrackerappws.exception.NotFoundException;
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
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public void createTask(Long userId, TaskDto taskDto) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(taskDto.getTitle());
        taskEntity.setDescription(taskDto.getDescription());
        taskEntity.setUserEntity(userEntity);
        taskRepository.save(taskEntity);
    }


    @Override
    public List<TaskDto> getAllTasks(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).
                orElseThrow(()->new NotFoundException(("User not found"));
        List<TaskEntity> tasks = taskRepository.findTasksByUser(userEntity);
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
                .orElseThrow(() -> new NotFoundException("User not found"));

        TaskEntity taskEntity = taskRepository.findTaskByIdAndStudent(taskId, userEntity)
                .orElseThrow(() -> new NotFoundException("Task Not Found"));

        TaskDto taskDto = new TaskDto();
        BeanUtils.copyProperties(taskEntity, taskDto);

        return taskDto;
    }

    @Override
    public List<TaskDto> getTasksByStatus(Long studentId, String status) {
        Status statusCheck = checkStatus(status);

        UserEntity userEntity = userRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        List<TaskEntity> tasks = taskRepository.findTaskByStatusAndStudent(statusCheck, userEntity);

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
                .orElseThrow(() -> new NotFoundException("User not found"));

        TaskEntity taskEntity = taskRepository.findTaskByIdAndStudent(taskId, userEntity)
                .orElseThrow(() -> new NotFoundException("Task Not Found"));

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
                .orElseThrow(() -> new NotFoundException("User not found"));

        TaskEntity taskEntity = taskRepository.findTaskByIdAndStudent(taskId, userEntity)
                .orElseThrow(() -> new NotFoundException("Task Not Found"));

        if(title != null && title.length() > 0 && !title.equals(taskEntity.getTitle())){
            taskEntity.setTitle(title);
            taskEntity.setUpdatedAt(LocalDateTime.now());
        }

        if(description != null && description.length() > 0 && !description.equals(taskEntity.getDescription())){
            taskEntity.setDescription(description);
            taskEntity.setUpdatedAt(LocalDateTime.now());
        }

        TaskDto taskDto = new TaskDto();
        BeanUtils.copyProperties(taskEntity, taskDto);

        return taskDto;
    }

    @Override
    public void deleteTask(Long studentId, Long taskId) {
            UserEntity userEntity = userRepository.findById(studentId)
                    .orElseThrow(() -> new NotFoundException("User not found"));

            taskRepository.findTaskByIdAndStudent(taskId, userEntity)
                    .ifPresentOrElse(
                            taskRepository::delete,
                            () -> {
                                throw new NotFoundException("Task Not Found");
                            });

        }

        private Status checkStatus(String status) {
            Status statusCheck;

            if(status.equalsIgnoreCase("pending")){
                statusCheck = Status.PENDING;
            } else if (status.equalsIgnoreCase("in_progress")) {
                statusCheck = Status.IN_PROGRESS;
            } else if (status.equalsIgnoreCase("done")){
                statusCheck = Status.DONE;
            } else {
                throw new BadRequestException("Invalid Status");
            }

            return statusCheck;
        }

        }


