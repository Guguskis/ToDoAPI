package lt.liutikas.todoapi.service.ProjectService;

import lt.liutikas.todoapi.dto.CreateProjectDto;
import lt.liutikas.todoapi.exception.EntityNotFoundException;
import lt.liutikas.todoapi.model.Project;
import lt.liutikas.todoapi.model.SimplifiedUserDto;
import lt.liutikas.todoapi.model.User;
import lt.liutikas.todoapi.repository.ProjectRepository;
import lt.liutikas.todoapi.repository.UserRepository;
import lt.liutikas.todoapi.service.ProjectUserService.ProjectUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        User user = userRepository.findByUsername(username);
        Optional<Project> project = projectRepository.findById(projectId);

        if (user == null) {
            throw new EntityNotFoundException("User was not found.");
        } else if (project.isEmpty()) {
            throw new EntityNotFoundException("Project was not found.");
        }

//        project.get().getMembers().add(user);
        projectRepository.save(project.get());
    }

    @Override
    public void create(CreateProjectDto dto) throws EntityNotFoundException {
        User user = userRepository.findByUsername(dto.getOwnerUsername());

        if (user == null) {
            throw new EntityNotFoundException("User does not exist");
        }

        Project project = new Project();
        project.setName(dto.getName());
        project.setOwnerId(user.getId());
//        project.getMembers().add(user);

        projectRepository.save(project);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<SimplifiedUserDto> findMembers(long projectId) {
        List<User> members = projectUserService.findMembers(projectId);
        List<SimplifiedUserDto> memberDtos = new ArrayList<>();

        for (User member : members) {
            SimplifiedUserDto dto = new SimplifiedUserDto();
            dto.setId(member.getId());
            dto.setUsername(member.getUsername());
            memberDtos.add(dto);
        }
        return memberDtos;
    }
}
