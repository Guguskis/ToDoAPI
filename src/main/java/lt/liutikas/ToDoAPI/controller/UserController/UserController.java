package lt.liutikas.ToDoAPI.controller.UserController;

import lt.liutikas.ToDoAPI.exception.DuplicateUserException;
import lt.liutikas.ToDoAPI.exception.UserNotFoundException;
import lt.liutikas.ToDoAPI.model.User;
import lt.liutikas.ToDoAPI.service.UserService.UserService;
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
public class UserController {

    @Autowired
    UserService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody User user) {
        try {
            service.create(user);
        } catch (DuplicateUserException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already taken");
        }

    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody User user) {
        try {
            service.update(user);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already taken");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") long id) {
        try {
            service.delete(id);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username id is invalid");
        }
    }

}
