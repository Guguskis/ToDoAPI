package lt.liutikas.todoapi.service.projectservice;

import lt.liutikas.todoapi.dto.AddMemberToProjectDto;
import lt.liutikas.todoapi.dto.CreateProjectDto;
import lt.liutikas.todoapi.dto.SessionUserDto;
import lt.liutikas.todoapi.dto.SimplifiedProjectDto;
import lt.liutikas.todoapi.exception.EntityNotFoundException;
import lt.liutikas.todoapi.model.Project;
import lt.liutikas.todoapi.model.User;
import lt.liutikas.todoapi.repository.ProjectRepository;
import lt.liutikas.todoapi.repository.UserRepository;
import lt.liutikas.todoapi.service.userservice.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultProjectService implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository; //Todo leave only service
    private final UserService userService;

    public DefaultProjectService(ProjectRepository repository, UserRepository userRepository, UserService userService) {
        this.projectRepository = repository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void addMember(AddMemberToProjectDto dto) throws EntityNotFoundException {
        Optional<User> user = userRepository.findByUsername(dto.getUsername());
        Optional<Project> project = projectRepository.findById(dto.getProjectId());

        if (user.isEmpty()) {
            throw new EntityNotFoundException("User was not found.");
        } else if (project.isEmpty()) {
            throw new EntityNotFoundException("Project was not found.");
        }

        project.get().addMember(user.get());
        projectRepository.save(project.get());
    }

    @Override
    public void create(CreateProjectDto dto) throws EntityNotFoundException {
        User owner = tryGetUser(dto.getOwnerUsername());
        List<User> members = tryGetMembers(dto, owner);
        Project project = getProject(dto, owner, members);

        projectRepository.save(project);
    }

    private Project getProject(CreateProjectDto dto, User owner, List<User> members) {
        Project project = new Project();
        project.setName(dto.getProjectName());
        project.setOwnerId(owner.getId());

        for (User member : members) {
            project.addMember(member);
        }
        return project;
    }

    private List<User> tryGetMembers(CreateProjectDto dto, User owner) throws EntityNotFoundException {
        List<User> members = new ArrayList<>();
        members.add(owner);

        List<String> membersNotFound = new ArrayList<>();
        for (String memberUsername : dto.getMembers()) {
            Optional<User> member = userRepository.findByUsername(memberUsername);
            if (member.isEmpty()) {
                membersNotFound.add(memberUsername);
            } else {
                members.add(member.get());
            }
        }

        if (!membersNotFound.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("These usernames were not found: ");
            builder.append(String.join(", ", membersNotFound));
            throw new EntityNotFoundException(builder.toString());
        }
        return members;
    }

    private User tryGetUser(String username) throws EntityNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            String message = "User " + username + " does not exist.";
            throw new EntityNotFoundException(message);
        }
        return user.get();
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<SessionUserDto> findMembers(long projectId) throws EntityNotFoundException {
        return tryGetProject(projectId)
                .getMembers()
                .stream()
                .map(this::getSimplifiedUserDto)
                .collect(Collectors.toList());
    }

    private Project tryGetProject(long id) throws EntityNotFoundException {
        Optional<Project> project = projectRepository.findById(id);

        if (project.isEmpty()) {
            throw new EntityNotFoundException("Project was not found");
        }

        return project.get();
    }

    @Override
    public List<Project> find(String username) throws EntityNotFoundException {
        User owner = tryGetUser(username);
        List<Project> projects = projectRepository.findAll();

        projects = projects
                .stream()
                .filter(project -> project.getOwnerId() == owner.getId())
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
        return getSimplifiedProjectsDto(userService.findUser(username));
    }

    private List<SimplifiedProjectDto> getSimplifiedProjectsDto(User user) throws EntityNotFoundException {
        List<SimplifiedProjectDto> projectsDto = new ArrayList<>();

        List<Project> projects = user.getProjects();
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
