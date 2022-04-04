package tasks;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(Integer id, String name, String description) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = TaskStatus.NEW;
        type = TaskType.EPIC;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void changeEpicStatus(TaskStatus status) {
        this.status = status;
    }
    public void updateSubtask(Subtask subtaskToUpdate) {
        for (int i = 0; i < subtasks.size(); i++) {
            if (subtasks.get(i).getId() == subtaskToUpdate.getId()) {
                subtasks.set(i, subtaskToUpdate);
            }
        }
    }

    public static Epic fromString(String epicString) {
        String[] epicAttributes = epicString.split(",");
        return new Epic(Integer.parseInt(epicAttributes[0]), epicAttributes[2], epicAttributes[4]);
    }
}
