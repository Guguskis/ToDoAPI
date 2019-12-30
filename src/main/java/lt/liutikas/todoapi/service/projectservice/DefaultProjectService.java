package lt.liutikas.todoapi.service.projectservice;

import lt.liutikas.todoapi.dto.CreateProjectDto;
import lt.liutikas.todoapi.dto.SessionUserDto;
import lt.liutikas.todoapi.dto.SimplifiedProjectDto;
import lt.liutikas.todoapi.exception.EntityNotFoundException;
import lt.liutikas.todoapi.model.Project;
import lt.liutikas.todoapi.model.User;
import lt.liutikas.todoapi.repository.ProjectRepository;
import lt.liutikas.todoapi.repository.UserRepository;
import lt.liutikas.todoapi.service.projectuserservice.ProjectUserService;
import lt.liutikas.todoapi.service.userservice.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultProjectService implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectUserService projectUserService;
    private final UserService userService;

    public DefaultProjectService(ProjectRepository repository, UserRepository userRepository, ProjectUserService projectUserService, UserService userService) {
        this.projectRepository = repository;
        this.userRepository = userRepository;
        this.projectUserService = projectUserService;
        this.userService = userService;
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

    @Override
    public List<SimplifiedProjectDto> findProjects(String username) throws EntityNotFoundException {
        return getSimplifiedProjectDtos(userService.findUser(username));
    }

    private List<SimplifiedProjectDto> getSimplifiedProjectDtos(User user) throws EntityNotFoundException {
        List<SimplifiedProjectDto> projectsDto = new ArrayList<>();

        List<Project> projects = projectUserService.findProjects(user.getId());
        for (Project project : projects) {
            projectsDto.add(getSimplifiedProjectDto(project));
        }
        return projectsDto;
    }

    private SimplifiedProjectDto getSimplifiedProjectDto(Project project) throws EntityNotFoundException {
        SimplifiedProjectDto dto = new SimplifiedProjectDto();
        String owner = userService.findUser(project.getOwnerId()).getUsername();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setOwner(owner);
        dto.setMemberCount(project.getMembers().size());
        return dto;
    }
}
