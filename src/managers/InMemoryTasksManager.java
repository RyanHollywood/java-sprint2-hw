package managers;

import tasks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTasksManager implements TaskManager {

    protected HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    protected HashMap<Integer, Task> tasks;
    protected HashMap<Integer, Subtask> subtasks;
    protected HashMap<Integer, Epic> epics;

    Integer id = 0;

    public InMemoryTasksManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
    }

    //Создание

    @Override
    public void createTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).addSubtask(subtask);
        changeEpicStatus(epics.get(subtask.getEpicId()));
    }

    // Получение списка всех задач
    @Override
    public List<Task> getAllTasks() {
        List<Task> allTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            allTasks.add(task);
        }
        return allTasks;
    }

    @Override
    public List<Epic> getAllEpics() {
        List<Epic> allEpics = new ArrayList<>();
        for (Epic epic : epics.values()) {
            allEpics.add(epic);
        }
        return allEpics;
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        List<Subtask> allSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            allSubtasks.add(subtask);
        }
        return allSubtasks;
    }

    //Удаление всех задач

    @Override
    public void clearAllTasks() {
        tasks.clear();
    }

    @Override
    public void clearAllEpics() {
        epics.clear();
    }

    @Override
    public void clearAllSubtasks() {
        subtasks.clear();
    }

    //Получение задачи по идентификатору

    @Override
    public Task getTaskById(Integer id) {
        inMemoryHistoryManager.add(id);
        return tasks.get(id);
    }

    @Override
    public Epic getEpicById(Integer id) {
        inMemoryHistoryManager.add(id);
        return epics.get(id);
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        inMemoryHistoryManager.add(id);
        return subtasks.get(id);
    }

    //обновление

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).updateSubtask(subtask);
        changeEpicStatus(epics.get(subtask.getEpicId()));
    }

    //Удаление по идентификатору

    @Override
    public void deleteTaskById(Integer id) {
        inMemoryHistoryManager.remove(id);
        tasks.remove(id);
    }

    @Override
    public void deleteEpicById(Integer id) {
        for(Subtask subtask : epics.get(id).getSubtasks()) {
            inMemoryHistoryManager.remove(subtask.getId());
        }
        inMemoryHistoryManager.remove(id);
        epics.remove(id);
    }

    @Override
    public void deleteSubtaskById(Integer id) {
        inMemoryHistoryManager.remove(id);
        subtasks.remove(id);
    }

    //Получение списка всех подзадач определенного эпика

    @Override
    public ArrayList<Subtask> getAllSubtasksByEpic(Epic epic) {
        ArrayList<Subtask> list = new ArrayList<>();
        list = epic.getSubtasks();
        return list;
    }

    @Override
    public Integer getNewId() {
        id++;
        return id;
    }

    public TaskType getTypeById(Integer id) {
        if (tasks.containsKey(id)) {
            return TaskType.TASK;
        } else if (epics.containsKey(id)) {
            return TaskType.EPIC;
        } else {
            return TaskType.SUBTASK;
        }
    }

    public List<Integer> history() {
        return inMemoryHistoryManager.getHistory();
    }

    //Изменение статуса эпика

    private void changeEpicStatus(Epic epic) {
        ArrayList<Subtask> epicSubtasks = epic.getSubtasks();
        //NEW and DONE
        int quantityOfNew = 0;
        int quantityOfDone = 0;
        for (Subtask subtask : epicSubtasks) {
            if (subtask.getStatus().equals(TaskStatus.NEW)) {
                quantityOfNew++;
            }
            if (subtask.getStatus().equals(TaskStatus.DONE)) {
                quantityOfDone++;
            }
        }
        if (quantityOfNew == epicSubtasks.size()) {
            epic.changeEpicStatus(TaskStatus.NEW);
        }
        else if (quantityOfDone == epicSubtasks.size()) {
            epic.changeEpicStatus(TaskStatus.DONE);
        }
        else {
            epic.changeEpicStatus(TaskStatus.IN_PROGRESS);
        }
    }
}
