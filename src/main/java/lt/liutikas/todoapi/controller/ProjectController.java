package lt.liutikas.todoapi.controller;

import io.swagger.annotations.Api;
import lt.liutikas.todoapi.dto.CreateProjectDto;
import lt.liutikas.todoapi.dto.SimplifiedUserDto;
import lt.liutikas.todoapi.exception.EntityNotFoundException;
import lt.liutikas.todoapi.model.Project;
import lt.liutikas.todoapi.service.projectservice.ProjectService;
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
@RequestMapping("/api/project")
@Api(tags = "project")
public class ProjectController {
    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody CreateProjectDto dto) {
        try {
            service.create(dto);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            System.out.println();
        }
    }

    @PostMapping("/addMember/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public void addUser(@PathVariable long projectId, @RequestBody String username) {
        try {
            service.addUser(projectId, username);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/members/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public List<SimplifiedUserDto> findMembers(@PathVariable long projectId) {
        return service.findMembers(projectId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Project> findAll() {
        return service.findAll();
    }
}
