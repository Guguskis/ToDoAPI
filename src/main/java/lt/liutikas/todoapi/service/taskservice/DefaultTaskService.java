package lt.liutikas.todoapi.service.taskservice;

import lt.liutikas.todoapi.dto.CreateTaskDto;
import lt.liutikas.todoapi.dto.SimplifiedTaskDto;
import lt.liutikas.todoapi.dto.UpdateTaskDto;
import lt.liutikas.todoapi.exception.EntityNotFoundException;
import lt.liutikas.todoapi.model.Project;
import lt.liutikas.todoapi.model.Task;
import lt.liutikas.todoapi.repository.ProjectRepository;
import lt.liutikas.todoapi.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultTaskService implements TaskService {
    private TaskRepository repository;
    private ProjectRepository projectRepository;

    public DefaultTaskService(TaskRepository repository, ProjectRepository projectRepository) {
        this.repository = repository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void createForProject(CreateTaskDto dto) throws EntityNotFoundException {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setProject(getProject(dto.getProjectId()));
        task.setCreatedBy(dto.getCreatedBy());
        repository.save(task);
    }

    private Project getProject(long projectId) throws EntityNotFoundException {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty()) {
            throw new EntityNotFoundException("Project was not found.");
        }
        return project.get();
    }

    @Override
    public List<SimplifiedTaskDto> find(long projectId) throws EntityNotFoundException {
        Project project = getProject(projectId);
        List<Task> tasks = repository.findByProject(project);
        List<SimplifiedTaskDto> tasksDto = new ArrayList<>();

        for (Task task : tasks) {
            tasksDto.add(getSimplifiedTaskDto(task));
        }

        return tasksDto;
    }

    @Override
    public void createForTask(CreateTaskDto dto) throws EntityNotFoundException {
        Task parentTask = getTask(dto.getTaskId());

        Task taskToCreate = new Task();
        taskToCreate.setTitle(dto.getTitle());
        taskToCreate.setCreatedBy(dto.getCreatedBy());

        parentTask.getTasks().add(taskToCreate);
        repository.save(parentTask);
    }

    @Override
    public void update(UpdateTaskDto dto) throws EntityNotFoundException {
        Task task = getTask(dto.getId());
        task.setTitle(dto.getTitle());

        if (wasNotCompleted(dto, task)) {
            task.setCompletedDate(LocalDateTime.now());
            task.setCompletedBy(dto.getUpdatorUsername());
            task.setCompleted(dto.isCompleted());
        }

        repository.save(task);
    }

    private boolean wasNotCompleted(UpdateTaskDto dto, Task task) {
        return !task.isCompleted() && dto.isCompleted();
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    private Task getTask(long taskId) throws EntityNotFoundException {
        Optional<Task> task = repository.findById(taskId);
        if (task.isEmpty()) {
            throw new EntityNotFoundException("Task was not found");
        }

        return task.get();
    }

    private SimplifiedTaskDto getSimplifiedTaskDto(Task task) {
        SimplifiedTaskDto dto = new SimplifiedTaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setCompleted(task.isCompleted());
        dto.setCompletedBy(task.getCompletedBy());
        dto.setCompletedDate(task.getCompletedDate());
        dto.setCreatedBy(task.getCreatedBy());
        dto.setCreatedDate(task.getCreatedDate());

        for (Task childTask : task.getTasks()) {
            dto.getTasks().add(getSimplifiedTaskDto(childTask));
        }

        return dto;
    }
}
