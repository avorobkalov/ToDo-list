package com.softserve.itacademy;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.service.StateService;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TaskControllerTest extends DefaultHandlerExceptionResolver {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private TaskService taskService;
    @Autowired
    private ToDoService todoService;
    @Autowired
    private StateService stateService;

    @Transactional
    @Test
    public void createTaskTest() throws Exception {
        ToDo todo = todoService.readById(7);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/create/todos/7"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("todo"))
                .andExpect(MockMvcResultMatchers.model().attribute("todo", todo));
    }
}

