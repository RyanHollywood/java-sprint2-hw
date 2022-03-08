package tasks;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(String name, String description, Integer id) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = "NEW";
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void changeEpicStatus(String status) {
        this.status = status;
    }
    public void updateSubtask(Subtask subtaskToUpdate) {
        for (int i = 0; i < subtasks.size(); i++) {
            if (subtasks.get(i).getId() == subtaskToUpdate.getId()) {
                subtasks.set(i, subtaskToUpdate);
            }
        }
    }
}
