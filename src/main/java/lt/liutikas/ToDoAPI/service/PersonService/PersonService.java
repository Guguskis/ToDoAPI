package lt.liutikas.ToDoAPI.service.PersonService;

import lt.liutikas.ToDoAPI.exception.DuplicatePersonException;
import lt.liutikas.ToDoAPI.exception.PersonNotFoundException;
import lt.liutikas.ToDoAPI.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> findAll();

    void create(Person person) throws DuplicatePersonException;

    void update(Person person) throws PersonNotFoundException;

    void delete(String username) throws PersonNotFoundException;
}
