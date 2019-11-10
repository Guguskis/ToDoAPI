package lt.liutikas.ToDoAPI.service.PersonService;

import lt.liutikas.ToDoAPI.exception.DuplicateEntityException;
import lt.liutikas.ToDoAPI.exception.EntityNotFoundException;
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
    public void create(Person person) throws DuplicateEntityException {
        if (exists(person.getUsername())) {
            throw new DuplicateEntityException("username is taken");
        } else {
            personRepository.save(person);
        }
    }

    private boolean exists(String username) {
        try {
            find(username);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    @Override
    public void update(Person person) throws EntityNotFoundException {
        Person existingPerson = find(person.getUsername());
        person.setId(existingPerson.getId());
        userRepository.save(person);
        personRepository.save(person);
    }

    private Person find(String username) throws EntityNotFoundException {
        Person person = personRepository.findByUsername(username);

        if (person == null) {
            throw new EntityNotFoundException("person not found");
        }

        return person;
    }

    @Override
    public void delete(String username) throws EntityNotFoundException {
        Person personToDelete = find(username);
        personRepository.delete(personToDelete);
    }

}
