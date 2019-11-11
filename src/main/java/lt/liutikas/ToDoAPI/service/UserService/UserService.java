package lt.liutikas.ToDoAPI.service.UserService;

import lt.liutikas.ToDoAPI.exception.EntityNotFoundException;
import lt.liutikas.ToDoAPI.model.User;

public interface UserService {
    boolean verify(User user);

    User find(String username) throws EntityNotFoundException;

    User find(long id);
}
