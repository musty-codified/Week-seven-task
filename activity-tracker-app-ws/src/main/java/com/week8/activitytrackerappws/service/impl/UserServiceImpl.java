package com.week8.activitytrackerappws.service.impl;

import com.week8.activitytrackerappws.dto.UserDto;
import com.week8.activitytrackerappws.entitiy.UserEntity;
import com.week8.activitytrackerappws.exception.BadRequestException;
import com.week8.activitytrackerappws.exception.NotFoundException;
import com.week8.activitytrackerappws.repository.UserRepository;
import com.week8.activitytrackerappws.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto user) {

        UserEntity userEntity= new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        boolean emailTaken = userRepository.existsByEmail(user.getEmail());

        if(emailTaken){
            throw new BadRequestException("Email Already Taken");
        }
        UserEntity savedUser = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(savedUser, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUser(UserDto user) {
        if(user.getEmail() == null || user.getPassword() == null){
            throw new BadRequestException("Complete All Fields");
        }

        UserEntity loginUser = userRepository.findUserByEmail(user.getEmail())
                .orElseThrow(() -> {
                    throw new NotFoundException("User Not Found");
                } );

        if(!loginUser.getPassword().equals(user.getPassword())){
            throw new BadRequestException("Password Incorrect");
        }
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(loginUser, returnValue);

        return returnValue;
    }
}