package com.week8.activitytrackerappws.service.impl;

import com.week8.activitytrackerappws.dto.TaskDto;
import com.week8.activitytrackerappws.enums.Status;
import com.week8.activitytrackerappws.model.TaskEntity;
import com.week8.activitytrackerappws.model.UserEntity;
import com.week8.activitytrackerappws.repository.TaskRepository;
import com.week8.activitytrackerappws.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskServiceImplTest {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private TaskServiceImpl underTest;


    @BeforeEach
    void setUp(){underTest = new TaskServiceImpl(taskRepository, userRepository);}
    @AfterEach
    void tearDown(){
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    void createTask(){
        UserEntity user = new UserEntity("Vivian", "Musa", "viva@gmail.com", "12");
        userRepository.save(user);
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("testing");
        taskDto.setDescription("describe testing");
        underTest.createTask(1L, taskDto);
        TaskEntity actual = taskRepository.findById(1L).get();
        assertEquals(1L, actual.getId());
        assertEquals(1L, actual.getUserEntity().getId());

    }

    @Test
    void getTask() {
        UserEntity user = new UserEntity( "Johnny", "Bull", "johnny@email.com", "1234");
        userRepository.save(user);

        user = userRepository.findUserByEmail("johnny@email.com").get();

        TaskEntity task = new TaskEntity("Title1", "Test Description", Status.PENDING, user);
        task = taskRepository.save(task);

        TaskDto actual = underTest.getTask(user.getId(), task.getId() );

        assertEquals(task.getTitle(), actual.getTitle());
        assertEquals(task.getCreatedAt(), actual.getCreatedAt());

    }

//    @Test
//    void getAllTasks() {
//
//        UserEntity userEntity = new UserEntity( "John", "Wick", "babayega@email.com", "1234");
//        userRepository.save(userEntity);
//        UserEntity user = new UserEntity( "Johnny", "Bull", "johnny@email.com", "1234");
//        userRepository.save(user);
//
//        userEntity = userRepository.findUserByEmail("babayega@email.com").get();
//        TaskEntity task = new TaskEntity("Title", "Test Description", Status.PENDING, userEntity);
//        TaskEntity task1 = new TaskEntity("Title", "Test Description", Status.PENDING, user);
//        TaskEntity task2 = new TaskEntity("Title", "Test Description", Status.PENDING, userEntity);
//        taskRepository.saveAll(List.of(task, task1, task2));
//
//        List<TaskDto> actual = underTest.getAllTasks(userEntity.getId());
//
//        assertEquals(2, actual.size());
//    }

    @Test
    void updateTask(){
        UserEntity userEntity = new UserEntity( "John", "Wick", "babayega@email.com", "1234");
        userRepository.save(userEntity);

        userEntity = userRepository.findUserByEmail("babayega@email.com").get();

        TaskEntity task = new TaskEntity("Title1", "Test Description", Status.PENDING, userEntity);
        task = taskRepository.save(task);

        TaskDto actual = underTest.updateTask(userEntity.getId(), task.getId(), "Title 2", "Test Description Updated");

        assertEquals("Title 2", actual.getTitle());
        assertEquals("Test Description Updated", actual.getDescription());
    }


    @Test
    void deleteTask(){
        UserEntity john = new UserEntity( "John", "Wick", "babayega@email.com", "1234");
        userRepository.save(john);

        john = userRepository.findUserByEmail("babayega@email.com").get();

        TaskEntity task = new TaskEntity("Title1", "Test Description", Status.PENDING, john);
        Long taskId = taskRepository.save(task).getId();

        underTest.deleteTask(john.getId(), task.getId());

        assertThrows(NoSuchElementException.class, () -> {
            taskRepository.findById(taskId).get();
    });

    }

    @Test
    void getTaskByStatus(){
        UserEntity userEntity = new UserEntity( "John", "Wick", "babayega@email.com", "1234");
        userRepository.save(userEntity);

        userEntity = userRepository.findUserByEmail("babayega@email.com").get();

        TaskEntity task = new TaskEntity("Title", "Test Description", Status.DONE, userEntity);
        TaskEntity task1 = new TaskEntity("Title1", "Test Description1", Status.IN_PROGRESS, userEntity);
        TaskEntity task2 = new TaskEntity("Title2", "Test Description2", Status.PENDING, userEntity);
        TaskEntity task3 = new TaskEntity("Title3", "Test Description3", Status.DONE, userEntity);
        taskRepository.saveAll(List.of(task, task1, task2, task3));

        List<TaskDto> actual = underTest.getTasksByStatus(userEntity.getId(), "done");

        assertEquals(2, actual.size());
    }
    @Test
    void updateTaskStatus(){
        UserEntity userEntity = new UserEntity( "John", "Wick", "babayega@email.com", "1234");
        userRepository.save(userEntity);

        userEntity = userRepository.findUserByEmail("babayega@email.com").get();

        TaskEntity task = new TaskEntity("Title1", "Test Description", Status.PENDING, userEntity);
        task = taskRepository.save(task);

        TaskDto actual = underTest.updateTaskStatus(userEntity.getId(), task.getId(), "done");

        assertEquals(Status.DONE, actual.getStatus());
    }
}
