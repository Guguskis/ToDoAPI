package lt.liutikas.ToDoAPI.service.PersonService;

import lt.liutikas.ToDoAPI.exception.DuplicatePersonException;
import lt.liutikas.ToDoAPI.exception.PersonNotFoundException;
import lt.liutikas.ToDoAPI.model.Person;
import lt.liutikas.ToDoAPI.repository.PersonRepository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultPersonService implements PersonService {
    @Autowired
    private PersonRepository repository;

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Override
    public void create(Person person) throws DuplicatePersonException {
        try {
            repository.save(person);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatePersonException();
        }
    }

    @Override
    public void update(Person person) throws PersonNotFoundException {
        Person existingPerson = find(person.getUsername());
        person.setId(existingPerson.getId());
        repository.save(person);
    }

    @Override
    public void delete(String username) throws PersonNotFoundException {
        var personToDelete = find(username);
        repository.delete(personToDelete);
    }

    public Person find(String username) throws PersonNotFoundException {
        Person person = repository.findByUsername(username);

        if (person == null) {
            throw new PersonNotFoundException();
        }

        return person;
    }


}
