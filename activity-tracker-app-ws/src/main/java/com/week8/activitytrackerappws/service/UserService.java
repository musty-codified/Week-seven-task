package com.week8.activitytrackerappws.service;

import com.week8.activitytrackerappws.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto getUser(UserDto user);
}
