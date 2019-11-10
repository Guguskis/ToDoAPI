package lt.liutikas.ToDoAPI.service.TaskService;

import lt.liutikas.ToDoAPI.dto.CreateTaskDTO;
import lt.liutikas.ToDoAPI.model.Task;

import java.util.List;

public interface TaskService {
    void create(CreateTaskDTO task);

    List<Task> findAll();
}
