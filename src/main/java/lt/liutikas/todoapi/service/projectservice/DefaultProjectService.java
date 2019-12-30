package lt.liutikas.todoapi.service.projectservice;

import lt.liutikas.todoapi.dto.CreateProjectDto;
import lt.liutikas.todoapi.dto.SessionUserDto;
import lt.liutikas.todoapi.exception.EntityNotFoundException;
import lt.liutikas.todoapi.model.Project;
import lt.liutikas.todoapi.model.User;
import lt.liutikas.todoapi.repository.ProjectRepository;
import lt.liutikas.todoapi.repository.UserRepository;
import lt.liutikas.todoapi.service.projectuserservice.ProjectUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultProjectService implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectUserService projectUserService;

    public DefaultProjectService(ProjectRepository repository, UserRepository userRepository, ProjectUserService projectUserService) {
        this.projectRepository = repository;
        this.userRepository = userRepository;
        this.projectUserService = projectUserService;
    }

    @Override
    public void addUser(long projectId, String username) throws EntityNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Project> project = projectRepository.findById(projectId);

        if (user.isEmpty()) {
            throw new EntityNotFoundException("User was not found.");
        } else if (project.isEmpty()) {
            throw new EntityNotFoundException("Project was not found.");
        }

//        project.get().getMembers().add(user);
        projectRepository.save(project.get());
    }

    @Override
    public void create(CreateProjectDto dto) throws EntityNotFoundException {
        Optional<User> user = userRepository.findByUsername(dto.getOwnerUsername());

        if (user.isEmpty()) {
            throw new EntityNotFoundException("User does not exist");
        }

        Project project = new Project();
        project.setName(dto.getName());
        project.setOwnerId(user.get().getId());
//        project.getMembers().add(user);

        projectRepository.save(project);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<SessionUserDto> findMembers(long projectId) {
        List<User> members = projectUserService.findMembers(projectId);

        return members
                .stream()
                .map(this::getSimplifiedUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Project> find(String username) {
        Optional<User> owner = userRepository.findByUsername(username);
        List<Project> projects = projectRepository.findAll();

        projects = projects
                .stream()
                .filter(project -> project.getOwnerId() == owner.get().getId())
                .collect(Collectors.toList());

        return projects;
    }

    private SessionUserDto getSimplifiedUserDto(User user) {
        SessionUserDto dto = new SessionUserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }
}
