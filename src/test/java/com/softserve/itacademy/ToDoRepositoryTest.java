package com.softserve.itacademy;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ToDoRepositoryTest {

    @Autowired
    ToDoRepository toDoRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    public void saveTest(){
        User user = new User();
        user.setFirstName("Mark");
        user.setLastName("One");
        ToDo toDo = new ToDo();
        toDo.setTitle("#1");
        toDo.setOwner(user);
        toDo.setCreatedAt(LocalDateTime.now());
        toDoRepository.save(toDo);
        ToDo actual = toDoRepository.getOne(1L);
        Assertions.assertEquals("#1", actual.getTitle());
    }

    @Test
    @Transactional
    public void findByIdTest(){
        User user = new User();
        user.setFirstName("Mark");
        user.setLastName("Two");
        user.setEmail("test@Gmail.com");
        user.setPassword("asddsa");
        userRepository.save(user);
        System.err.println(user);
        ToDo toDo = new ToDo();
        toDo.setTitle("#1");
        toDo.setOwner(userRepository.findByEmail("test@Gmail.com"));
        toDo.setCreatedAt(LocalDateTime.now());
        toDoRepository.save(toDo);
        System.err.println(toDo);
        ToDo actual = toDoRepository.getOne(1L);
        Assertions.assertEquals(1L, actual.getId());
    }

    @Test
    @Transactional
    public void deleteTest(){
        User user = new User();
        user.setFirstName("Mark");
        user.setLastName("Three");
        user.setPassword("asddsa");
        user.setEmail("test@Gmail.com");
        userRepository.save(user);
        System.err.println(user);
        ToDo toDo = new ToDo();
        toDo.setTitle("#1");
        toDo.setOwner(userRepository.findByEmail("test@Gmail.com"));
        toDo.setCreatedAt(LocalDateTime.now());
        toDoRepository.save(toDo);
        toDoRepository.delete(toDo);
        Assertions.assertNull(userRepository.findByEmail("test@Gmail.com").getMyTodos());
    }

    @Test
    @Transactional
    public void findAllTest(){
        List<ToDo> expected = new ArrayList<>();
        User user = new User();
        user.setFirstName("Mark");
        user.setLastName("Four");
        user.setPassword("asddsa");
        user.setEmail("test@Gmail.com");
        userRepository.save(user);
        System.err.println(user);
        ToDo toDo = new ToDo();
        toDo.setTitle("#1");
        toDo.setOwner(userRepository.findByEmail("test@Gmail.com"));
        toDo.setCreatedAt(LocalDateTime.now());
        toDoRepository.save(toDo);
        ToDo toDo1 = new ToDo();
        toDo1.setTitle("#2");
        toDo1.setOwner(userRepository.findByEmail("test@Gmail.com"));
        toDo1.setCreatedAt(LocalDateTime.now());
        toDoRepository.save(toDo1);
        expected.add(toDo);
        expected.add(toDo1);
        List<ToDo> actual = toDoRepository.findAll();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    @Transactional
    public void getByUserIdTest(){
        List<ToDo> expected = new ArrayList<>();
        User user = new User();
        user.setFirstName("Mark");
        user.setLastName("Five");
        user.setPassword("asddsa");
        user.setEmail("test@Gmail.com");
        userRepository.save(user);
        System.err.println(user);
        ToDo toDo = new ToDo();
        toDo.setTitle("#1");
        toDo.setOwner(userRepository.findByEmail("test@Gmail.com"));
        toDo.setCreatedAt(LocalDateTime.now());
        toDoRepository.save(toDo);
        ToDo toDo1 = new ToDo();
        toDo1.setTitle("#2");
        toDo1.setOwner(userRepository.findByEmail("test@Gmail.com"));
        toDo1.setCreatedAt(LocalDateTime.now());
        toDoRepository.save(toDo1);
        expected.add(toDo1);
        expected.add(toDo);
        List<ToDo> actual = toDoRepository.getByUserId(4L);
        Assertions.assertEquals(expected,actual);
    }
}