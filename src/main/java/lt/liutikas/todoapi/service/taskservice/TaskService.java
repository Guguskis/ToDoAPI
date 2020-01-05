package lt.liutikas.todoapi.service.taskservice;

import lt.liutikas.todoapi.dto.CreateTaskDto;
import lt.liutikas.todoapi.dto.SimplifiedTaskDto;
import lt.liutikas.todoapi.dto.UpdateTaskDto;
import lt.liutikas.todoapi.exception.EntityNotFoundException;

import java.util.List;

public interface TaskService {
    void createForProject(CreateTaskDto dto) throws EntityNotFoundException;

    List<SimplifiedTaskDto> find(long projectId) throws EntityNotFoundException;

    void createForTask(CreateTaskDto dto) throws EntityNotFoundException;

    void update(UpdateTaskDto dto) throws EntityNotFoundException;

    void delete(long id);
}
