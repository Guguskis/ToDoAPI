package lt.liutikas.todoapi.controller;

import lt.liutikas.todoapi.dto.CreateProjectUserDto;
import lt.liutikas.todoapi.service.projectuserservice.ProjectUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/projectuser")
public class ProjectUserController {
    private final ProjectUserService service;

    public ProjectUserController(ProjectUserService service) {
        this.service = service;
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
