import managers.FileBackedTasksManager;
import managers.InMemoryTasksManager;
import managers.Managers;
import managers.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FileBackedTasksManager tasksManager = FileBackedTasksManager.loadFromFile("history.txt");
        printHistory(tasksManager);

        /*
        Тестирование старого менеджера

        TaskManager tasksManager = Managers.getDefault();

        //Эпик с двумя подзадачами

        Epic epic1 = new Epic(tasksManager.getNewId(),"Epic1", "Description epic1");
        tasksManager.createEpic(epic1);

        Subtask subtask1 = new Subtask(tasksManager.getNewId(), "Subtask1", TaskStatus.NEW,
                "Description subtask1",epic1.getId());
        tasksManager.createSubtask(subtask1);

        Subtask subtask2 = new Subtask(tasksManager.getNewId(), "Subtask2", TaskStatus.DONE,
                "Description subtask2",epic1.getId());
        tasksManager.createSubtask(subtask2);

        //Две задачи

        Task task1 = new Task(tasksManager.getNewId(),"Task1", TaskStatus.NEW,"Description task1");
        tasksManager.createTask(task1);
        Task task2 = new Task(tasksManager.getNewId(),"Task2", TaskStatus.NEW,"Description task2");
        tasksManager.createTask(task2);

        //Просмотр задач

        tasksManager.getEpicById(epic1.getId());
        tasksManager.getSubtaskById(subtask1.getId());
        tasksManager.getSubtaskById(subtask2.getId());
        tasksManager.getTaskById(task1.getId());
        tasksManager.getTaskById(task2.getId());

        printHistory(tasksManager);
         */
    }

    static void printAllTasks(TaskManager taskManager) {
        //Распечатать списки всех эпиков, задач и подзадач
        List<Task> listOfAllTasks = taskManager.getAllTasks();
        for (Task task : listOfAllTasks) {
            System.out.println(task.getName() + " " + task.getDescription() + " " + task.getStatus());
        }
        List<Epic> listOfAllEpics = taskManager.getAllEpics();
        for (Epic epic : listOfAllEpics) {
            System.out.println(epic.getName() + " " + epic.getDescription() + " " + epic.getStatus());
        }
        List<Subtask> listOfAllSubtasks = taskManager.getAllSubtasks();
        for (Subtask subtask : listOfAllSubtasks) {
            System.out.println(subtask.getName() + " " + subtask.getDescription() + " " + subtask.getStatus());
        }
        System.out.println();
    }

    static void printSubtasksOfEpic(TaskManager taskManager, Epic epic) {
        List<Subtask> listOfAllSubtaskByEpic = taskManager.getAllSubtasksByEpic(epic);
        for (Subtask subtask : listOfAllSubtaskByEpic) {
            System.out.println(subtask.getName());
        }
    }

    static void printHistory(TaskManager inMemoryTaskManager) {
        List<Integer> history = inMemoryTaskManager.history();
        String[] historyArray = new String[history.size()];
        for (int i = 0; i < history.size(); i++) {
            historyArray[i] = history.get(i).toString();
        }
        System.out.println(String.join(",", historyArray));
    }
}
