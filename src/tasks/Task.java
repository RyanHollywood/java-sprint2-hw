package tasks;

public class Task {

    protected String name;
    protected String description;
    protected Integer id;
    protected TaskStatus status;


    public Task(String name, String description, Integer id, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public Task() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }


}
