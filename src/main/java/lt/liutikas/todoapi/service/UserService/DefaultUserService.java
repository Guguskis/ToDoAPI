package lt.liutikas.todoapi.service.UserService;

import lt.liutikas.todoapi.exception.DuplicateEntityException;
import lt.liutikas.todoapi.exception.EntityNotFoundException;
import lt.liutikas.todoapi.model.Company;
import lt.liutikas.todoapi.model.Person;
import lt.liutikas.todoapi.model.User;
import lt.liutikas.todoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return checkExceptions(user);
    }

    @Override
    public User find(long id) throws EntityNotFoundException {
        User user = repository.findById(id);
        return checkExceptions(user);
    }

    private User checkExceptions(User user) throws EntityNotFoundException {
        if (user == null) {
            throw new EntityNotFoundException("user not found");
        }

        return user;
    }

    @Override
    public void create(Person person) throws DuplicateEntityException {
        try {
            repository.save(person);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException("username is taken");
        }
    }

    @Override
    public void create(Company company) throws DuplicateEntityException {
        try {
            repository.save(company);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException("username is taken");
        }
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    private boolean verificationIsSuccessful(User user, User userInDatabase) {
        return user.getUsername().equals(userInDatabase.getUsername())
                && user.getPassword().equals(userInDatabase.getPassword());
    }
}
