package tasks;

public class Subtask extends Task {

    private Epic epic;


    public Subtask(String name, String description, Integer id, TaskStatus status, Epic epic) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }


}
