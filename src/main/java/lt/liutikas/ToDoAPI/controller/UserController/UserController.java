package lt.liutikas.ToDoAPI.controller.UserController;

import lt.liutikas.ToDoAPI.exception.UserNotFoundException;
import lt.liutikas.ToDoAPI.model.User;
import lt.liutikas.ToDoAPI.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User find(@PathVariable long id) {
        try {
            return service.find(id);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
        }
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public User find(@PathVariable String username) {
        try {
            return service.find(username);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
        }
    }


}
