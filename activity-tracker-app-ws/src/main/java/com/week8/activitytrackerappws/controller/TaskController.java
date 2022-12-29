package com.week8.activitytrackerappws.controller;


import com.week8.activitytrackerappws.dto.TaskDto;
import com.week8.activitytrackerappws.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class TaskController {

   private TaskService taskService;

   @RequestMapping(method = RequestMethod.POST, value = "/{userId}/addTask")

    @PostMapping("/{userId}/addTask")
    public ResponseEntity<String> createTask(@PathVariable Long userId, @RequestBody TaskDto taskDto) {
        taskService.createTask(userId, taskDto);
        return new ResponseEntity<>("Task created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/tasks/{userId}")
    public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable Long userId) {
        List<TaskDto> tasks = taskService.getAllTasks(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{foo}/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("foo") Long userId, @PathVariable Long taskId) {
        TaskDto task = taskService.getTask(userId, taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("{userId}/{taskId}/status")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable Long userId,
                                                    @PathVariable Long taskId,
                                                    @RequestParam("status") String status) {

        TaskDto task = taskService.updateTaskStatus(userId, taskId, status);
        return new ResponseEntity<>(task, HttpStatus.OK);

    }
    @PutMapping("{userId}/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long userId,
                                              @PathVariable Long taskId,
                                              @RequestParam(value = "title", required = false) String title,
                                              @RequestParam(value = "description", required = false) String description){

        TaskDto task = taskService.updateTask(userId, taskId, title, description);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
    @DeleteMapping("/{userId}/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long userId,
                                             @PathVariable Long taskId){

        taskService.deleteTask(userId, taskId);
        return new ResponseEntity<>("Task Deleted Successfully", HttpStatus.OK);
    }

}