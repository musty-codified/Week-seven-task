package com.week8.activitytrackerappws.controller;


import com.week8.activitytrackerappws.dto.TaskDto;
import com.week8.activitytrackerappws.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("{/userId}/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable ){
        List<TaskDto> tasks = taskService.getAllTasks(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);


    }

}
