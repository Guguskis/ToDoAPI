package lt.liutikas.todoapi.service.userservice;

import lt.liutikas.todoapi.dto.CreatePersonDto;
import lt.liutikas.todoapi.dto.UpdateCompanyDto;
import lt.liutikas.todoapi.dto.UpdatePersonDto;
import lt.liutikas.todoapi.dto.VerifyUserDto;
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
    public boolean verify(VerifyUserDto userToVerify) {
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
            throw new EntityNotFoundException("User " + username + "not found");
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
    public void create(CreatePersonDto dto) throws DuplicateEntityException {
        try {
            userRepository.save(getPerson(dto));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException("Username is taken");
        }
    }

    private Person getPerson(CreatePersonDto dto) {
        Person person = new Person();
        person.setUsername(dto.getUsername());
        person.setPassword(dto.getPassword());
        person.setLastName(dto.getLastName());
        person.setFirstName(dto.getFirstName());
        person.setPhone(dto.getPhone());
        person.setEmail(dto.getEmail());
        return person;
    }

    @Override
    public void create(Company company) throws DuplicateEntityException {

        try {
            userRepository.save(company);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException("username is taken");
        }
    }

    private Company getCompany(UpdateCompanyDto dto) throws EntityNotFoundException {
        Company company = (Company) findUser(dto.getUsername());
        company.setId(dto.getId());
        company.setUsername(dto.getUsername());
        company.setPassword(dto.getPassword());
        company.setName(dto.getName());
        company.setContactPersonPhone(dto.getContactPersonPhone());
        return company;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void update(UpdatePersonDto dto) throws EntityNotFoundException {
        userRepository.save(getPerson(dto));
    }

    private Person getPerson(UpdatePersonDto dto) throws EntityNotFoundException {
        Person person = (Person) findUser(dto.getUsername());
        person.setId(dto.getId());
        person.setUsername(dto.getUsername());
        person.setPassword(dto.getPassword());
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setPhone(dto.getPhone());
        person.setEmail(dto.getEmail());
        return person;
    }

    @Override
    public void update(UpdateCompanyDto company) throws EntityNotFoundException {
        userRepository.save(getCompany(company));
    }

    private boolean verificationIsSuccessful(VerifyUserDto user, User userInDatabase) {
        return user.getUsername().equals(userInDatabase.getUsername())
                && user.getPassword().equals(userInDatabase.getPassword());
    }
}
