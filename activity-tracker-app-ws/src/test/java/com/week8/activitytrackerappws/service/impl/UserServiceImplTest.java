package com.week8.activitytrackerappws.service.impl;
import com.week8.activitytrackerappws.dto.UserDto;
import com.week8.activitytrackerappws.model.UserEntity;
import com.week8.activitytrackerappws.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AllArgsConstructor
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceImplTest {
    private UserServiceImpl userService;
    private final UserRepository userRepository;
    @BeforeEach
    void setUp(){
        userService = new UserServiceImpl(userRepository);
        }

    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }
    @Test
    final void testGetUser(){
        UserEntity userEntity = new UserEntity("Habiba", "Musa", "test@test.com", "biba2000");
        UserDto user = new UserDto("Habiba", "Musa", "test@test.com", "biba2000");
        userRepository.save(userEntity);
        UserDto actual = userService.getUser(user);
        assertEquals("Habiba", actual.getFirstName());
        assertEquals("Musa", actual.getLastName());
        assertEquals("test@test.com", actual.getEmail());

    }

    @Test
    final void testCreateUser(){
        UserDto userDto = new UserDto("Mustapha", "Musa", "email@email.com", "papi01");
        UserDto actual = userService.createUser(userDto);
        assertEquals(userDto, actual);
    }

}
