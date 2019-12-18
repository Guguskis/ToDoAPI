package lt.liutikas.todoapi.service.UserService;

import lt.liutikas.todoapi.dto.SimplifiedProjectDto;
import lt.liutikas.todoapi.exception.DuplicateEntityException;
import lt.liutikas.todoapi.exception.EntityNotFoundException;
import lt.liutikas.todoapi.model.Company;
import lt.liutikas.todoapi.model.Person;
import lt.liutikas.todoapi.model.Project;
import lt.liutikas.todoapi.model.User;
import lt.liutikas.todoapi.repository.UserRepository;
import lt.liutikas.todoapi.service.ProjectUserService.ProjectUserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final ProjectUserService projectUserService;

    public DefaultUserService(UserRepository userRepository, ProjectUserService projectUserService) {
        this.userRepository = userRepository;
        this.projectUserService = projectUserService;
    }

    @Override
    public boolean verify(User user) {
        try {
            User userInDatabase = findUser(user.getUsername());
            return verificationIsSuccessful(user, userInDatabase);
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    @Override
    public User findUser(String username) throws EntityNotFoundException {
        User user = userRepository.findByUsername(username);
        return checkExceptions(user);
    }

    @Override
    public User findUser(long id) throws EntityNotFoundException {
        User user = userRepository.findById(id);
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
            userRepository.save(person);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException("username is taken");
        }
    }

    @Override
    public void create(Company company) throws DuplicateEntityException {
        try {
            userRepository.save(company);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException("username is taken");
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<SimplifiedProjectDto> findProjects(String username) throws EntityNotFoundException {
        return getSimplifiedProjectDtos(findUser(username));
    }

    private List<SimplifiedProjectDto> getSimplifiedProjectDtos(User user) throws EntityNotFoundException {
        List<SimplifiedProjectDto> projectsDtos = new ArrayList<>();

        List<Project> projects = projectUserService.findProjects(user.getId());
        for (Project project : projects) {
            SimplifiedProjectDto dto = new SimplifiedProjectDto();
            dto.setId(project.getId());
            dto.setName(project.getName());
            String owner = findUser(project.getOwnerId()).getUsername();
            dto.setOwner(owner);
            projectsDtos.add(dto);
        }
        return projectsDtos;
    }

    private boolean verificationIsSuccessful(User user, User userInDatabase) {
        return user.getUsername().equals(userInDatabase.getUsername())
                && user.getPassword().equals(userInDatabase.getPassword());
    }
}
