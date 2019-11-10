package lt.liutikas.ToDoAPI.service.UserService;

import lt.liutikas.ToDoAPI.exception.EntityNotFoundException;
import lt.liutikas.ToDoAPI.model.User;
import lt.liutikas.ToDoAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {
    @Autowired
    UserRepository repository;

    @Override
    public boolean login(User user) {
        try {
            User userInDatabase = find(user.getUsername());
            return verificationIsSuccessful(user, userInDatabase);
        } catch (EntityNotFoundException e) {
            return false;
        }
    }


    public User find(String username) throws EntityNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null) {
            throw new EntityNotFoundException("user not found");
        }

        return user;
    }


    private boolean verificationIsSuccessful(User user, User userInDatabase) {
        return user.getUsername().equals(userInDatabase.getUsername())
                && user.getPassword().equals(userInDatabase.getPassword());
    }
}
