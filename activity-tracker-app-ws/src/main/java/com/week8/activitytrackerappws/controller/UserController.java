package com.week8.activitytrackerappws.controller;


import com.week8.activitytrackerappws.dto.UserDto;
import com.week8.activitytrackerappws.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users") //http:localhost:8081/users
@AllArgsConstructor
public class UserController {
   private final UserService userService;
   @GetMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto user){
      UserDto loginUser= userService.getUser(user);
       return new ResponseEntity<>( loginUser, HttpStatus.OK);
   }

   @PostMapping("/signup")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user){

       UserDto createdUser = userService.createUser(user);


       return new ResponseEntity<>(createdUser, HttpStatus.CREATED);

   }
}
