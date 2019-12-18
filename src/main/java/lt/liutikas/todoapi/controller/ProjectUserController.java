package lt.liutikas.todoapi.controller;

import lt.liutikas.todoapi.dto.CreateProjectUserDto;
import lt.liutikas.todoapi.model.ProjectUser;
import lt.liutikas.todoapi.model.User;
import lt.liutikas.todoapi.service.ProjectUserService.ProjectUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/projectuser")
public class ProjectUserController {
    private final ProjectUserService service;

    public ProjectUserController(ProjectUserService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectUser> findAll() {
        return service.findAll();
    }

    @GetMapping("/user/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> findUsers(@PathVariable long projectId) {
        return service.findMembers(projectId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody CreateProjectUserDto dto) {
        try {
            service.create(dto);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User or project do not exist.");
        }
    }

}
