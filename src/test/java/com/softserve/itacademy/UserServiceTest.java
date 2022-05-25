package com.softserve.itacademy;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void createUserTest(){
        User user = new User();
        user.setFirstName("Mark");
        user.setLastName("One");
        user.setEmail("testUser@gmail.com");
        user.setPassword("pass");
        userService.create(user);
        User actual = userRepository.findByEmail("testUser@gmail.com");
        Assertions.assertEquals("Bondar",actual.getLastName());
    }

    @Test
    @Transactional
    public void readByIdTest(){
        User user = new User();
        user.setFirstName("Mark");
        user.setLastName("Two");
        user.setEmail("testUser@gmail.com");
        user.setPassword("pass");
        userService.create(user);
        System.err.println(userRepository.findByEmail("testUser@gmail.com").getId());
        User actual = userService.readById(2L);
        Assertions.assertEquals("Dmitriy", actual.getFirstName());
    }

    //ToDo needs to check security
//    @Test
//    @Transactional
//    public void updateTest(){
//        User user = new User();
//        user.setFirstName("Dmitriy");
//        user.setLastName("Bondar");
//        user.setEmail("testUser@gmail.com");
//        user.setPassword("pass");
//        userService.create(user);
//        user.setFirstName("Andrey");
//        userService.update(user);
//        User actual = userService.readById(1L);
//        Assertions.assertEquals("Andrey", actual.getFirstName());
//    }

    @Test
    @Transactional
    public void deleteTest(){
        User user = new User();
        user.setFirstName("Mark");
        user.setLastName("Four");
        user.setEmail("testUser@gmail.com");
        user.setPassword("pass");
        userService.create(user);
        System.err.println(userRepository.findByEmail("testUser@gmail.com").getId());
        userService.delete(7L);
        User actual = userRepository.findByEmail("testUser@gmail.com");
        Assertions.assertNull(actual);
    }

    @Test
    @Transactional
    public void getAllTest(){
        User user = new User();
        user.setFirstName("Mark");
        user.setLastName("One");
        user.setEmail("testUser@gmail.com");
        user.setPassword("pass");
        userService.create(user);
        User user1 = new User();
        user1.setFirstName("Mark");
        user1.setLastName("Two");
        user1.setEmail("testUser1@gmail.com");
        user1.setPassword("pass");
        userService.create(user1);
        User user2 = new User();
        user2.setFirstName("Mark");
        user2.setLastName("Three");
        user2.setEmail("testUser2@gmail.com");
        user2.setPassword("pass");
        userService.create(user2);
        userService.delete(10L);
        List<User> expectedUserList = new ArrayList<>();
        expectedUserList.add(user);
        expectedUserList.add(user1);
        expectedUserList.add(user2);
        List<User> actualUserList = new ArrayList<>(userService.getAll());
        Assertions.assertEquals(expectedUserList,actualUserList);
    }
}