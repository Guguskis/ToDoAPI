package lt.liutikas.todoapi.service.ProjectUserService;

import lt.liutikas.todoapi.dto.CreateProjectUserDto;
import lt.liutikas.todoapi.model.Project;
import lt.liutikas.todoapi.model.ProjectUser;
import lt.liutikas.todoapi.model.User;
import lt.liutikas.todoapi.repository.ProjectRepository;
import lt.liutikas.todoapi.repository.ProjectUserRepository;
import lt.liutikas.todoapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultProjectUserService implements ProjectUserService {
    private ProjectUserRepository projectUserRepository;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;

    public DefaultProjectUserService(ProjectUserRepository projectUserRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.projectUserRepository = projectUserRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }


    @Override
    public List<Project> findProjects(long userId) {
        List<ProjectUser> unmappedProjects = projectUserRepository.findByUserId(userId);
        List<Project> mappedProjects = new ArrayList<>();

        for (ProjectUser unmappedProject : unmappedProjects) {
            long projectId = unmappedProject.getProjectId();
            Project project = projectRepository.findById(projectId).get();
            mappedProjects.add(project);
        }

        return mappedProjects;
    }

    @Override
    public List<User> findMembers(long projectId) {
        List<ProjectUser> unmappedUsers = projectUserRepository.findByProjectId(projectId);
        List<User> mappedUsers = new ArrayList<>();

        for (ProjectUser unmappedUser : unmappedUsers) {
            long userId = unmappedUser.getUserId();
            User user = userRepository.findById(userId);
            mappedUsers.add(user);
        }

        return mappedUsers;
    }

    @Override
    public void create(CreateProjectUserDto dto) {
        ProjectUser projectUser = new ProjectUser();
        projectUser.setUserId(dto.getUserId());
        projectUser.setProjectId(dto.getProjectId());
        projectUserRepository.save(projectUser);
    }

}
