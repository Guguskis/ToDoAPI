package lt.liutikas.todoapi.service.ProjectService;

import lt.liutikas.todoapi.dto.CreateProjectDto;
import lt.liutikas.todoapi.exception.EntityNotFoundException;
import lt.liutikas.todoapi.model.Project;
import lt.liutikas.todoapi.model.SimplifiedUserDto;

import java.util.List;

public interface ProjectService {
    void addUser(long projectId, String username) throws EntityNotFoundException;

    void create(CreateProjectDto dto) throws EntityNotFoundException;

    List<Project> findAll();

    List<SimplifiedUserDto> findMembers(long projectId);
}
