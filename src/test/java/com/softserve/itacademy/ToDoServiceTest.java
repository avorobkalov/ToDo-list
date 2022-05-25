package com.softserve.itacademy;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.ToDoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ToDoServiceTest {

    @Autowired
    ToDoRepository toDoRepository;

    @Autowired
    ToDoService toDoService;

    @Autowired
    UserRepository userRepository;

    //ToDo sometimes in random order
    @Test
    @Transactional
    public void createTest(){
        List<ToDo> expected = new ArrayList<>();
        User user = new User();
        user.setFirstName("Mark");
        user.setLastName("One");
        user.setEmail("test@Gmail.com");
        user.setPassword("asddsa");
        userRepository.save(user);
        ToDo toDo = new ToDo();
        toDo.setTitle("#1");
        toDo.setOwner(userRepository.findByEmail("test@Gmail.com"));
        toDo.setCreatedAt(LocalDateTime.now());
        toDoService.create(toDo);
        ToDo toDo1 = new ToDo();
        toDo1.setTitle("#2");
        toDo1.setOwner(userRepository.findByEmail("test@Gmail.com"));
        toDo1.setCreatedAt(LocalDateTime.now());
        toDoService.create(toDo1);
        expected.add(toDo);
        expected.add(toDo1);
        List<ToDo> actual = toDoService.getByUserId(userRepository.findByEmail("test@Gmail.com").getId());
        Assertions.assertEquals(expected,actual);
    }
}