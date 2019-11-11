package lt.liutikas.ToDoAPI.service.UserService;

import lt.liutikas.ToDoAPI.exception.EntityNotFoundException;
import lt.liutikas.ToDoAPI.model.User;
import lt.liutikas.ToDoAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public boolean verify(User user) {
        try {
            User userInDatabase = find(user.getUsername());
            return verificationIsSuccessful(user, userInDatabase);
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    @Override
    public User find(String username) throws EntityNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null) {
            throw new EntityNotFoundException("user not found");
        }

        return user;
    }

    @Override
    public User find(long id) {
        return repository.findById(id);
    }


    private boolean verificationIsSuccessful(User user, User userInDatabase) {
        return user.getUsername().equals(userInDatabase.getUsername())
                && user.getPassword().equals(userInDatabase.getPassword());
    }
}
