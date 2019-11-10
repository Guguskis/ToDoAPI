package lt.liutikas.ToDoAPI.controller;

import lt.liutikas.ToDoAPI.model.User;
import lt.liutikas.ToDoAPI.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    boolean login(@RequestBody User user) {
        return service.login(user);
    }


}
