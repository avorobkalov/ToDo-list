package com.softserve.itacademy;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void getAllTest() throws Exception{
        List<User> expected = userService.getAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/users/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", expected));
    }

    @Test
    @Transactional
    public void createTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                .param("first_name","Mark")
                .param("last_name", "One")
                .param("email", "testUser@gmail.com")
                .param("role_id", "2")
                .param("password", "pass"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @Transactional
    public void deleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/10/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        User actual = userRepository.findByEmail("testUser@gmail.com");
        Assertions.assertNull(actual);
    }
}
