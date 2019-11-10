package lt.liutikas.ToDoAPI.service.PersonService;

import lt.liutikas.ToDoAPI.exception.DuplicatePersonException;
import lt.liutikas.ToDoAPI.exception.PersonNotFoundException;
import lt.liutikas.ToDoAPI.model.Person;
import lt.liutikas.ToDoAPI.repository.PersonRepository;
import lt.liutikas.ToDoAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultPersonService implements PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public void create(Person person) throws DuplicatePersonException {
        if (exists(person.getUsername())) {
            throw new DuplicatePersonException("username is taken");
        } else {
            personRepository.save(person);
        }
    }

    public boolean exists(String username) {
        try {
            find(username);
            return true;
        } catch (PersonNotFoundException e) {
            return false;
        }
    }

    @Override
    public void update(Person person) throws PersonNotFoundException {
        Person existingPerson = find(person.getUsername());
        person.setId(existingPerson.getId());
        userRepository.save(person);
        personRepository.save(person);
    }

    public Person find(String username) throws PersonNotFoundException {
        Person person = personRepository.findByUsername(username);

        if (person == null) {
            throw new PersonNotFoundException();
        }

        return person;
    }

    @Override
    public void delete(String username) throws PersonNotFoundException {
        var personToDelete = find(username);
        personRepository.delete(personToDelete);
    }

}
