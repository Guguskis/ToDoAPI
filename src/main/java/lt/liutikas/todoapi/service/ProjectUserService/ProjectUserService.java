package lt.liutikas.todoapi.service.ProjectUserService;

import lt.liutikas.todoapi.dto.CreateProjectUserDto;
import lt.liutikas.todoapi.model.Project;
import lt.liutikas.todoapi.model.User;

import java.util.List;

public interface ProjectUserService {
    List<Project> findProjects(long userId);

    List<User> findMembers(long projectId);

    void create(CreateProjectUserDto dto);

}
