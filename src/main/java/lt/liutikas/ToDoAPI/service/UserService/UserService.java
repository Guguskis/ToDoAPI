package lt.liutikas.ToDoAPI.service.UserService;

import lt.liutikas.ToDoAPI.exception.EntityNotFoundException;
import lt.liutikas.ToDoAPI.model.User;

public interface UserService {
    boolean login(User user);

    boolean exist(String username);

    User find(String username) throws EntityNotFoundException;
}
