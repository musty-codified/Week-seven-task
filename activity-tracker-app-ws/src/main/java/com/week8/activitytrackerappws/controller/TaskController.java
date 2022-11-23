package com.week8.activitytrackerappws.controller;


import com.week8.activitytrackerappws.dto.TaskDto;
import com.week8.activitytrackerappws.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("{/userId}/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable Long userId) {
        List<TaskDto> tasks = taskService.getAllTasks(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<List<TaskDto>> createTask(@PathVariable Long userId, @RequestBody TaskDto taskDto) {
        taskService.createTask(userId, taskDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long userId, @PathVariable Long taskId) {
        TaskDto task = taskService.getTask(userId, taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("{taskId}/status")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable Long userId,
                                                    @PathVariable Long taskId,
                                                    @RequestParam("status") String status) {

        TaskDto task = taskService.updateTaskStatus(userId, taskId, status);
        return new ResponseEntity<>(task, HttpStatus.OK);

    }
    @PutMapping("{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long userId,
                                              @PathVariable Long taskId,
                                              @RequestParam(value = "title", required = false) String title,
                                              @RequestParam(value = "description", required = false) String description){

        TaskDto task = taskService.updateTask(userId, taskId, title, description);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
    @DeleteMapping("{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long userId,
                                             @PathVariable Long taskId){

        taskService.deleteTask(userId, taskId);
        return new ResponseEntity<>("Task Deleted Successfully", HttpStatus.NO_CONTENT);
    }

}