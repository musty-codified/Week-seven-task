package com.week8.activitytrackerappws.service;

import com.week8.activitytrackerappws.dto.TaskDto;
import com.week8.activitytrackerappws.model.TaskEntity;

import java.util.List;

public interface TaskService {
    TaskEntity createTask(Long userId, TaskDto taskDto);
    List<TaskDto> getAllTasks(Long userId);

    TaskDto getTask(Long userId, Long taskId);

    List<TaskDto> getTasksByStatus(Long userId, String status);

    TaskDto updateTaskStatus(Long userId, Long taskId, String status);

    TaskDto updateTask(Long userId, Long taskId, String title, String description);

    void deleteTask(Long userId, Long taskId);
}
