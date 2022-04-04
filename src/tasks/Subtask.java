package tasks;

public class Subtask extends Task {

    private int epicId;


    public Subtask( Integer id, String name, TaskStatus status, String description, int epicId) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.epicId = epicId;
        type = TaskType.SUBTASK;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return id + "," + type + "," + name + "," + status + "," + description + "," + epicId;
    }

    public static Subtask fromString(String subtaskString) {
        String[] subtaskAttributes = subtaskString.split(",");
        return new Subtask(Integer.parseInt(subtaskAttributes[0]), subtaskAttributes[2], getTaskStatus(subtaskAttributes[3]),
                subtaskAttributes[4], Integer.parseInt(subtaskAttributes[5]));
    }
}
