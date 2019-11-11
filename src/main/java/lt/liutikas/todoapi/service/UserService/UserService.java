package lt.liutikas.todoapi.service.UserService;

import lt.liutikas.todoapi.exception.DuplicateEntityException;
import lt.liutikas.todoapi.exception.EntityNotFoundException;
import lt.liutikas.todoapi.model.Company;
import lt.liutikas.todoapi.model.Person;
import lt.liutikas.todoapi.model.User;

import java.util.List;

public interface UserService {
    boolean verify(User user);

    User find(String username) throws EntityNotFoundException;

    User find(long id) throws EntityNotFoundException;

    void create(Person person) throws DuplicateEntityException;

    void create(Company company) throws DuplicateEntityException;

    List<User> findAll();
}
