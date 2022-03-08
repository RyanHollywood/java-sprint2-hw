package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {

    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Subtask> subtasks;
    private HashMap<Integer, Epic> epics;

    Integer id = 0;

    public TaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
    }

    // Получение списка всех задач

    public List<Task> getAllTasks() {
        List<Task> allTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            allTasks.add(task);
        }
        return allTasks;
    }

    public List<Epic> getAllEpics() {
        List<Epic> allEpics = new ArrayList<>();
        for (Epic epic : epics.values()) {
            allEpics.add(epic);
        }
        return allEpics;
    }

    public List<Subtask> getAllSubtasks() {
        List<Subtask> allSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            allSubtasks.add(subtask);
        }
        return allSubtasks;
    }

    //Удаление всех задач

    public void clearAllTasks() {
        tasks.clear();
    }

    public void clearAllEpics() {
        epics.clear();
    }

    public void clearAllSubtasks() {
        subtasks.clear();
    }

    //Получение задачи по идентификатору

    public Task getTaskById(Integer id) {
        return tasks.get(id);
    }

    public Epic getEpicById(Integer id) {
        return epics.get(id);
    }

    public Subtask getSubtaskById(Integer id) {
        return subtasks.get(id);
    }

    //Создание

    public void createTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epics.put(epic.getId(), epic);

    }

    public void createSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpic().getId()).addSubtask(subtask);
        changeEpicStatus(epics.get(subtask.getEpic().getId()));
    }

    //обновление

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpic().getId()).updateSubtask(subtask);
        changeEpicStatus(epics.get(subtask.getEpic().getId()));
    }

    //Удаление по идентификатору

    public void deleteTaskById(Integer id) {
        tasks.remove(id);
    }

    public void deleteEpicById(Integer id) {
        epics.remove(id);
    }

    public void deleteSubtaskById(Integer id) {
        subtasks.remove(id);
    }

    //Получение списка всех подзадач определенного эпика

    public List<Subtask> getAllSubtasksByEpic(Epic epic) {
        List<Subtask> list = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (epic.getId() == (subtask.getEpic().getId())) {
                list.add(subtask);
            }
        }
        return list;
    }



    public Integer getNewId() {
        id++;
        return id;
    }

    //Изменение статуса эпика

    private void changeEpicStatus(Epic epic) {
        ArrayList<Subtask> epicSubtasks = epic.getSubtasks();
        //NEW and DONE
        int quantityOfNew = 0;
        int quantityOfDone = 0;
        for (Subtask subtask : epicSubtasks) {
            if (subtask.getStatus().equals("NEW")) {
                quantityOfNew++;
            }
            if (subtask.getStatus().equals("DONE")) {
                quantityOfDone++;
            }
        }
        if (quantityOfNew == epicSubtasks.size()) {
            epic.changeEpicStatus("NEW");
        }
        else if (quantityOfDone == epicSubtasks.size()) {
            epic.changeEpicStatus("DONE");
        }
        else {
            epic.changeEpicStatus("IN_PROGRESS");
        }
    }
}
