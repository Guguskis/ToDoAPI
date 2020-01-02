package lt.liutikas.todoapi.service.userservice;

import lt.liutikas.todoapi.exception.DuplicateEntityException;
import lt.liutikas.todoapi.exception.EntityNotFoundException;
import lt.liutikas.todoapi.model.Company;
import lt.liutikas.todoapi.model.Person;
import lt.liutikas.todoapi.model.User;
import lt.liutikas.todoapi.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean verify(User userToVerify) {
        try {
            User userInDatabase = findUser(userToVerify.getUsername());
            return verificationIsSuccessful(userToVerify, userInDatabase);
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    @Override
    public User findUser(String username) throws EntityNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new EntityNotFoundException("user not found");
        }

        return user.get();
    }

    @Override
    public User findUser(long id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }

        return user.get();
    }

    @Override
    public void create(Person person) throws DuplicateEntityException {
        try {
            userRepository.save(person);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException("Username is taken");
        }
    }

    @Override
    public void create(Company company) throws DuplicateEntityException {
        try {
            userRepository.save(company);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException("username is taken");
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void update(Person person) {
        userRepository.save(person);
    }

    @Override
    public void update(Company company) {
        userRepository.save(company);
    }

    private boolean verificationIsSuccessful(User user, User userInDatabase) {
        return user.getUsername().equals(userInDatabase.getUsername())
                && user.getPassword().equals(userInDatabase.getPassword());
    }
}
