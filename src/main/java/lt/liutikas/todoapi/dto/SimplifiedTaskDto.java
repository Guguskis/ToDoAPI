package lt.liutikas.todoapi.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SimplifiedTaskDto {
    private long id;
    private String title;
    private boolean completed;

    private String createdBy;
    private LocalDateTime createdDate;
    private String completedBy;
    private LocalDateTime completedDate;

    private List<SimplifiedTaskDto> tasks = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public List<SimplifiedTaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<SimplifiedTaskDto> tasks) {
        this.tasks = tasks;
    }
}
