package lt.liutikas.ToDoAPI.service.UserService;

import lt.liutikas.ToDoAPI.exception.UserNotFoundException;
import lt.liutikas.ToDoAPI.model.User;
import lt.liutikas.ToDoAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultUserService implements UserService {
    @Autowired
    UserRepository repository;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    private long getId(String username) throws UserNotFoundException {
        User user = find(username);
        return user.getId();
    }

    @Override
    public User find(String username) throws UserNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }

    @Override
    public boolean login(User user) {
        try {
            User userInDatabase = find(user.getUsername());
            return checkCredentials(user, userInDatabase);
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    private boolean checkCredentials(User user, User userInDatabase) {
        return user.getUsername().equals(userInDatabase.getUsername())
                && user.getPassword().equals(userInDatabase.getPassword());
    }


    @Override
    public User find(long id) throws UserNotFoundException {
        User user = repository.findById(id);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }

}
