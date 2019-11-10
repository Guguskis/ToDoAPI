package lt.liutikas.ToDoAPI.controller;

import lt.liutikas.ToDoAPI.dto.CreateTaskDTO;
import lt.liutikas.ToDoAPI.model.Task;
import lt.liutikas.ToDoAPI.service.TaskService.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    TaskService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Task> findAll() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateTaskDTO task) {
        service.create(task);
    }


}
