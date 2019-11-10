package lt.liutikas.ToDoAPI.service.PersonService;

import lt.liutikas.ToDoAPI.exception.DuplicateEntityException;
import lt.liutikas.ToDoAPI.exception.EntityNotFoundException;
import lt.liutikas.ToDoAPI.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> findAll();

    void create(Person person) throws DuplicateEntityException;

    void update(Person person) throws EntityNotFoundException;

    void delete(String username) throws EntityNotFoundException;
}
