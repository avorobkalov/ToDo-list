package com.softserve.itacademy;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;


    @Test
    @Transactional
    public void saveUserTest(){
        User user = new User();
        user.setFirstName("Mark");
        user.setLastName("One");
        user.setEmail("testUser@gmail.com");
        user.setPassword("pass");
        userRepository.save(user);
        System.err.println(userRepository.findByEmail("testUser@gmail.com").getId());
        User actual = userRepository.findByEmail("testUser@gmail.com");
        Assertions.assertEquals("Dmitriy",actual.getFirstName());
    }


    @Test
    @Transactional
    public void findByIdTest(){
        User user = new User();
        user.setFirstName("Mark");
        user.setLastName("One");
        user.setEmail("testUser@gmail.com");
        user.setPassword("pass");
        userRepository.save(user);
        System.err.println(userRepository.findByEmail("testUser@gmail.com").getId());
        Optional<User> optional = userRepository.findById(6L);
        Assertions.assertEquals("Dmitriy", optional.get().getFirstName());
    }


    @Test
    @Transactional
    public void updateTest(){
        User user = new User();
        user.setFirstName("Dmitriy");
        user.setLastName("Bondar");
        user.setEmail("testUser@gmail.com");
        user.setPassword("pass");
        userRepository.save(user);
        user.setLastName("Vakulenko");
        userRepository.save(user);
        System.err.println(userRepository.findByEmail("testUser@gmail.com").getId());
        User actual = userRepository.findByEmail("testUser@gmail.com");
        Assertions.assertEquals("Vakulenko",actual.getLastName());
    }


    @Test
    @Transactional
    public void deleteTest(){
        User user = new User();
        user.setFirstName("Dmitriy");
        user.setLastName("Bondar");
        user.setEmail("testUser@gmail.com");
        user.setPassword("pass");
        userRepository.save(user);
        System.err.println(userRepository.findByEmail("testUser@gmail.com").getId());
        userRepository.delete(user);
        User actual = userRepository.findByEmail("testUser@gmail.com");
        Assertions.assertNull(actual);
    }

    @Test
    @Transactional
    public void findAllTest(){
        User user = new User();
        user.setFirstName("Dmitriy");
        user.setLastName("Bondar");
        user.setEmail("testUser@gmail.com");
        user.setPassword("pass");
        userRepository.save(user);
        User user1 = new User();
        user1.setFirstName("Andrey");
        user1.setLastName("Vorobkalov");
        user1.setEmail("testUser1@gmail.com");
        user1.setPassword("pass");
        userRepository.save(user1);
        User user2 = new User();
        user2.setFirstName("Yaroslav");
        user2.setLastName("Makarenko");
        user2.setEmail("testUser2@gmail.com");
        user2.setPassword("pass");
        userRepository.save(user2);
        List<User> expectedUserList = new ArrayList<>();
        expectedUserList.add(user);
        expectedUserList.add(user1);
        expectedUserList.add(user2);
        userRepository.deleteById(10L);
        List<User> actualUserList = new ArrayList<>(userRepository.findAll());
        Assertions.assertEquals(expectedUserList,actualUserList);
    }
}