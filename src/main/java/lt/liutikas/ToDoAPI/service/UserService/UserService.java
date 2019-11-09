package lt.liutikas.ToDoAPI.service.UserService;

import lt.liutikas.ToDoAPI.exception.DuplicateUserException;
import lt.liutikas.ToDoAPI.exception.UserNotFoundException;
import lt.liutikas.ToDoAPI.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    void create(User user) throws DuplicateUserException;

    void update(User user) throws UserNotFoundException;

    void delete(long id) throws UserNotFoundException;
}
