package lt.liutikas.todoapi.repository;

import lt.liutikas.todoapi.model.Project;
import lt.liutikas.todoapi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProject(Project project);
}