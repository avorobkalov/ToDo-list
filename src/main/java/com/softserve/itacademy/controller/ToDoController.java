package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.security.SecurityUser;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/todos")
public class ToDoController {

    private final ToDoService todoService;
    private final TaskService taskService;
    private final UserService userService;

    public ToDoController(ToDoService todoService, TaskService taskService, UserService userService) {
        this.todoService = todoService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/create/users/{owner_id}")
    public String create(@PathVariable("owner_id") long ownerId, Model model) {
        checkLogged(ownerId);
        model.addAttribute("todo", new ToDo());
        model.addAttribute("ownerId", ownerId);
        return "create-todo";
    }

    @PostMapping("/create/users/{owner_id}")
    public String create(@PathVariable("owner_id") long ownerId, @Validated @ModelAttribute("todo") ToDo todo, Model model, BindingResult result) {
        checkLogged(ownerId);
        if (result.hasErrors()) {
            return "create-todo";
        }
        todo.setCreatedAt(LocalDateTime.now());
        todo.setOwner(userService.readById(ownerId));
        todoService.create(todo);
        return "redirect:/todos/all/users/" + ownerId;
    }

    @GetMapping("/{id}/tasks")
    public String read(@PathVariable long id, Model model) {
        ToDo todo = todoService.readById(id);
        checkCollaborator(id);
        List<Task> tasks = taskService.getByTodoId(id);
        List<User> users = userService.getAll().stream()
                .filter(user -> user.getId() != todo.getOwner().getId()).collect(Collectors.toList());
        model.addAttribute("todo", todo);
        model.addAttribute("tasks", tasks);
        model.addAttribute("users", users);
        return "todo-tasks";
    }

    @GetMapping("/{todo_id}/update/users/{owner_id}")
    public String update(@PathVariable("todo_id") long todoId, @PathVariable("owner_id") long ownerId, Model model) {
        checkLogged(
                todoService.readById(todoId).getOwner().getId()
        );
        ToDo todo = todoService.readById(todoId);
        model.addAttribute("todo", todo);
        return "update-todo";
    }

    @PostMapping("/{todo_id}/update/users/{owner_id}")
    public String update(@PathVariable("todo_id") long todoId, @PathVariable("owner_id") long ownerId,
                         @Validated @ModelAttribute("todo") ToDo todo, BindingResult result) {
        checkLogged(
                todoService.readById(todoId).getOwner().getId()
        );
        if (result.hasErrors()) {
            todo.setOwner(userService.readById(ownerId));
            return "update-todo";
        }
        ToDo oldTodo = todoService.readById(todoId);
        todo.setOwner(oldTodo.getOwner());
        todo.setCollaborators(oldTodo.getCollaborators());
        todoService.update(todo);
        return "redirect:/todos/all/users/" + ownerId;
    }

    @GetMapping("/{todo_id}/delete/users/{owner_id}")
    public String delete(@PathVariable("todo_id") long todoId, @PathVariable("owner_id") long ownerId) {
        checkLogged(
                todoService.readById(todoId).getOwner().getId()
        );
        todoService.delete(todoId);
        return "redirect:/todos/all/users/" + ownerId;
    }

    @GetMapping("/all/users/{user_id}")
    public String getAll(@PathVariable("user_id") long userId, Model model) {
        checkLogged(userId);
        List<ToDo> todos = todoService.getByUserId(userId);
        model.addAttribute("todos", todos);
        model.addAttribute("user", userService.readById(userId));
        return "todos-user";
    }

    @GetMapping("/{id}/add")
    public String addCollaborator(@PathVariable long id, @RequestParam("user_id") long userId) {
        ToDo todo = todoService.readById(id);
        checkLogged(todo.getOwner().getId());
        List<User> collaborators = todo.getCollaborators();
        collaborators.add(userService.readById(userId));
        todo.setCollaborators(collaborators);
        todoService.update(todo);
        return "redirect:/todos/" + id + "/tasks";
    }

    @GetMapping("/{id}/remove")
    public String removeCollaborator(@PathVariable long id, @RequestParam("user_id") long userId) {
        ToDo todo = todoService.readById(id);
        checkLogged(todo.getOwner().getId());
        List<User> collaborators = todo.getCollaborators();
        collaborators.remove(userService.readById(userId));
        todo.setCollaborators(collaborators);
        todoService.update(todo);
        return "redirect:/todos/" + id + "/tasks";
    }

    public void checkLogged(long id) {
        User logged = userService.readById(((SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        if (logged.getId() != id
                && !logged.getRole().getName().equals("ADMIN")) {
            throw new AccessDeniedException("");
        }
    }

    public void checkCollaborator(long id) {
        ToDo toDo = todoService.readById(id);
        User owner = toDo.getOwner();
        List<User> collaborators = toDo.getCollaborators();
        User logged = userService.readById(((SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        if (logged.getId() != owner.getId()
                && !logged.getRole().getName().equals("ADMIN")
                && !collaborators.stream().map(User::getId).anyMatch(x -> x == logged.getId())) {
            throw new AccessDeniedException("");
        }
    }
}
