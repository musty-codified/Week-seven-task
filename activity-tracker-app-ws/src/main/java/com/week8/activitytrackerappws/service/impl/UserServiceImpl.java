package com.week8.activitytrackerappws.service.impl;

import com.week8.activitytrackerappws.dto.UserDto;
import com.week8.activitytrackerappws.exception.ErrorMessages;
import com.week8.activitytrackerappws.model.UserEntity;
import com.week8.activitytrackerappws.exception.UserServiceException;
import com.week8.activitytrackerappws.exception.BadRequestException;
import com.week8.activitytrackerappws.repository.UserRepository;
import com.week8.activitytrackerappws.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
   private final UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto user) {

        UserEntity userEntity= new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        boolean emailTaken = userRepository.existsByEmail(user.getEmail());

        if(emailTaken){
            throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXIST.getErrorMessage());
        }
        UserEntity savedUser = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(savedUser, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUser(UserDto user) {
        if(user.getEmail().isEmpty() || user.getPassword().isEmpty()){
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        UserEntity loginUser = userRepository.findUserByEmail(user.getEmail())
                .orElseThrow(() -> {
                    throw new BadRequestException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
                } );

        if(!loginUser.getPassword().equals(user.getPassword())){
            throw new UserServiceException(ErrorMessages.INCORRECT_LOGIN_DETAILS.getErrorMessage());
        }
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(loginUser, returnValue);

        return returnValue;
    }
}