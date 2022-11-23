package com.week8.activitytrackerappws.service;

import com.week8.activitytrackerappws.dto.TaskDto;

import java.util.List;

public interface TaskService {
    List<TaskDto> getAllTasks(Long userId);
    void createTask(Long studentId, TaskDto taskDto);

    TaskDto getTask(Long studentId, Long taskId);

    List<TaskDto> getTasksByStatus(Long studentId, String status);

    TaskDto updateTaskStatus(Long studentId, Long taskId, String status);

    TaskDto updateTask(Long studentId, Long taskId, String title, String description);

    void deleteTask(Long studentId, Long taskId);
}
