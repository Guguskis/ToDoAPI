package lt.liutikas.todoapi.service.userservice;

import lt.liutikas.todoapi.dto.SimplifiedProjectDto;
import lt.liutikas.todoapi.exception.DuplicateEntityException;
import lt.liutikas.todoapi.exception.EntityNotFoundException;
import lt.liutikas.todoapi.model.Company;
import lt.liutikas.todoapi.model.Person;
import lt.liutikas.todoapi.model.Project;
import lt.liutikas.todoapi.model.User;
import lt.liutikas.todoapi.repository.UserRepository;
import lt.liutikas.todoapi.service.projectuserservice.ProjectUserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final ProjectUserService projectUserService;

    public DefaultUserService(UserRepository userRepository, ProjectUserService projectUserService) {
        this.userRepository = userRepository;
        this.projectUserService = projectUserService;
    }

    @Override
    public boolean verify(User userToVerify) {
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
            throw new EntityNotFoundException("user not found");
        }

        return user.get();
    }

    @Override
    public User findUser(long id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new EntityNotFoundException("user not found");
        }

        return user.get();
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

    @Override
    public void update(Person person) {
        userRepository.save(person);
        //Todo just have one update for User
    }

    @Override
    public void update(Company company) {
        userRepository.save(company);
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
