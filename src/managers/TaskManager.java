package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface TaskManager {

    // Получение списка всех задач

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    //Удаление всех задач

    void clearAllTasks();

    void clearAllEpics();

    void clearAllSubtasks();

    //Получение задачи по идентификатору

    Task getTaskById(Integer id);

    Epic getEpicById(Integer id);

    Subtask getSubtaskById(Integer id);

    //Создание

    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    //обновление

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    //Удаление по идентификатору

    void deleteTaskById(Integer id);

    void deleteEpicById(Integer id);

    void deleteSubtaskById(Integer id);

    //Получение списка всех подзадач определенного эпика

    ArrayList<Subtask> getAllSubtasksByEpic(Epic epic);

    Integer getNewId();

    LinkedList<Task> history();


}