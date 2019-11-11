package lt.liutikas.todoapi.service.TaskService;

import lt.liutikas.todoapi.dto.CreateTaskDTO;
import lt.liutikas.todoapi.model.Task;

import java.util.List;

public interface TaskService {
    void create(CreateTaskDTO task);

    List<Task> findAll();
}
