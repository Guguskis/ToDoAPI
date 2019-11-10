package lt.liutikas.ToDoAPI.controller;

import lt.liutikas.ToDoAPI.exception.DuplicatePersonException;
import lt.liutikas.ToDoAPI.exception.PersonNotFoundException;
import lt.liutikas.ToDoAPI.model.Person;
import lt.liutikas.ToDoAPI.service.PersonService.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    PersonService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Person> findAll() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Person person) {
        try {
            service.create(person);
        } catch (DuplicatePersonException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Person person) {
        try {
            service.update(person);
        } catch (PersonNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("username") String username) {
        try {
            service.delete(username);
        } catch (PersonNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
