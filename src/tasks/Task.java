package tasks;

public class Task {

    protected String name;
    protected String description;
    protected Integer id;
    protected TaskStatus status;
    protected TaskType type;


    public Task(Integer id, String name, TaskStatus status, String description) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        type = TaskType.TASK;
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

    protected static TaskStatus getTaskStatus(String taskTypeStr) {
        if (taskTypeStr.equals("NEW")) {
            return TaskStatus.NEW;
        } else if (taskTypeStr.equals("IN_PROGRESS")) {
            return TaskStatus.IN_PROGRESS;
        } else {
            return TaskStatus.DONE;
        }
    }

    public String toString() {
        return id + "," + type + "," + name + "," + status + "," + description;
    }

    public static Task fromString(String taskString) {
        String[] taskAttributes = taskString.split(",");
        return new Task(Integer.parseInt(taskAttributes[0]), taskAttributes[2], getTaskStatus(taskAttributes[3]),
                taskAttributes[4]);
    }
}
