package lt.liutikas.ToDoAPI.service.UserService;

import lt.liutikas.ToDoAPI.exception.DuplicateUserException;
import lt.liutikas.ToDoAPI.exception.UserNotFoundException;
import lt.liutikas.ToDoAPI.model.User;
import lt.liutikas.ToDoAPI.repository.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Override
    public void create(User user) throws DuplicateUserException {
        if (!exist(user.getUsername())) {
            repository.save(user);
        } else {
            throw new DuplicateUserException();
        }
    }

    @Override
    public void update(User user) throws UserNotFoundException {
        if (!exist(user.getUsername())) {
            repository.save(user);
        } else {
            throw new UserNotFoundException();
        }

    }

    @Override
    public void delete(long id) throws UserNotFoundException {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException();
        }
    }

    private boolean exist(String username) {
        User oldUser = repository.findByUsername(username);
        return oldUser != null;
    }
}
