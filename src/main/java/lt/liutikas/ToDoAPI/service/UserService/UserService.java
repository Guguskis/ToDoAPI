package lt.liutikas.ToDoAPI.service.UserService;

import lt.liutikas.ToDoAPI.exception.UserNotFoundException;
import lt.liutikas.ToDoAPI.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User find(long id) throws UserNotFoundException;

    User find(String username) throws UserNotFoundException;
}
