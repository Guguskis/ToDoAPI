package lt.liutikas.todoapi.service.personservice;

import lt.liutikas.todoapi.exception.DuplicateEntityException;
import lt.liutikas.todoapi.exception.EntityNotFoundException;
import lt.liutikas.todoapi.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> findAll();

    void create(Person person) throws DuplicateEntityException;

    void update(Person person) throws EntityNotFoundException;

    void delete(String username) throws EntityNotFoundException;
}
