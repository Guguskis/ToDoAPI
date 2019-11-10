package lt.liutikas.ToDoAPI.model;

import java.util.List;

public class Project {
    private String name;
    private User owner;
    private List<User> members;
    private List<Task> tasks;
}
