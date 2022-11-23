package com.week8.activitytrackerappws.service;

import com.week8.activitytrackerappws.dto.TaskDto;

import java.util.List;

public interface TaskService {
    void createTask(Long studentId, TaskDto taskDto);
    List<TaskDto> getAllTasks(Long userId);

    TaskDto getTask(Long studentId, Long taskId);

    List<TaskDto> getTasksByStatus(Long studentId, String status);

    TaskDto updateTaskStatus(Long studentId, Long taskId, String status);

    TaskDto updateTask(Long userId, Long taskId, String title, String description);

    void deleteTask(Long studentId, Long taskId);
}
