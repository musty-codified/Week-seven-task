package com.week8.activitytrackerappws.service.impl;

import com.week8.activitytrackerappws.dto.TaskDto;
import com.week8.activitytrackerappws.dto.UserDto;
import com.week8.activitytrackerappws.entitiy.TaskEntity;
import com.week8.activitytrackerappws.exception.NotFoundException;
import com.week8.activitytrackerappws.repository.TaskRepository;
import com.week8.activitytrackerappws.repository.UserRepository;
import com.week8.activitytrackerappws.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;
    UserRepository userRepository;
    @Override
    public List<TaskDto> getAllTasks(Long userId) {
        UserDto userDto = userRepository.findById(userId).
                orElseThrow(()->new NotFoundException(("User not found"));
        List<TaskEntity> tasks = taskRepository.findTasksByUser(userEntity);
        List<TaskDto> taskDtoList = new ArrayList<>();

        tasks.forEach(task -> {
            System.out.println("------>" + task);
            TaskDto taskDto = new TaskDto();
            BeanUtils.copyProperties(task, taskDto);
            taskDtoList.add(taskDto);
    }
}
