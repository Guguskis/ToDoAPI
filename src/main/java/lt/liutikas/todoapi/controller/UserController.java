package lt.liutikas.todoapi.controller;

import lt.liutikas.todoapi.exception.DuplicateEntityException;
import lt.liutikas.todoapi.exception.EntityNotFoundException;
import lt.liutikas.todoapi.model.Company;
import lt.liutikas.todoapi.model.Person;
import lt.liutikas.todoapi.model.User;
import lt.liutikas.todoapi.service.userservice.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public boolean verify(@RequestBody User user) {
        return service.verify(user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public User find(@PathVariable String username) {
        try {
            return service.findUser(username);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Person person) {
        try {
            service.create(person);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/company")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Company company) {
        try {
            service.create(company);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/person")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Person person) {
        service.update(person);
    }

    @PutMapping("/company")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Company company) {
        service.update(company);
    }

}
