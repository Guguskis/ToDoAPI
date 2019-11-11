package lt.liutikas.todoapi.service.TaskService;

import lt.liutikas.todoapi.controller.UserController;
import lt.liutikas.todoapi.dto.CreateTaskDTO;
import lt.liutikas.todoapi.model.Task;
import lt.liutikas.todoapi.model.User;
import lt.liutikas.todoapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DefaultTaskService implements TaskService {
    @Autowired
    TaskRepository repository;

    @Autowired
    UserController userController;

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public void create(CreateTaskDTO task) throws ResponseStatusException {
        User creator = userController.find(task.getCreatorUsername());
        repository.save(new Task(
                task.getTitle(),
                creator.getUsername()
        ));
    }


}
