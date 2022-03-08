import managers.Managers;
import managers.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        TaskManager inMemoryTaskManager = Managers.getDefault();

        //Тестирование

        //Эпик с двумя подзадачами

        Epic epic1 = new Epic("Epic1", "Epic1-description", inMemoryTaskManager.getNewId());
        inMemoryTaskManager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Subtask1", "Subtask1-description", inMemoryTaskManager.getNewId(), TaskStatus.NEW, epic1);
        inMemoryTaskManager.createSubtask(subtask1);

        Subtask subtask2 = new Subtask("Subtask2", "Subtask2-description", inMemoryTaskManager.getNewId(), TaskStatus.NEW, epic1);
        inMemoryTaskManager.createSubtask(subtask2);

        //Создание задач для заполнения истории

        ArrayList<Task> tasksTestList = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            String name = "Task" + i;
            String description = name + "-description";
            tasksTestList.add(new Task(name, description, inMemoryTaskManager.getNewId(), TaskStatus.NEW));
        }

        for (Task task : tasksTestList) {
            inMemoryTaskManager.createTask(task);
        }

        //10 просмотров

        for (int i = 0; i < tasksTestList.size() - 1; i++) {
            inMemoryTaskManager.getTaskById(tasksTestList.get(i).getId());
        }

        //Вывод истории просмотров

        printHistory(inMemoryTaskManager);

        //11 просмотр

        inMemoryTaskManager.getTaskById(tasksTestList.get(tasksTestList.size()-1).getId());

        //Вывод истории просмотров

        printHistory(inMemoryTaskManager);

        //Просмотр эпика и подзадач

        inMemoryTaskManager.getEpicById(epic1.getId());
        inMemoryTaskManager.getSubtaskById(subtask1.getId());
        inMemoryTaskManager.getSubtaskById(subtask2.getId());

        //Вывод истории просмотров

        printHistory(inMemoryTaskManager);

        //Повторный просмотр задачи

        inMemoryTaskManager.getTaskById(tasksTestList.get(0).getId());

        //Вывод истории просмотров

        printHistory(inMemoryTaskManager);

        //Удаление эпика

        inMemoryTaskManager.deleteEpicById(epic1.getId());

        //inMemoryTaskManager.deleteSubtaskById(subtask1.getId());

        //Вывод истории просмотров

        printHistory(inMemoryTaskManager);

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
        List<Task> history = inMemoryTaskManager.history();
        for (Task task : history) {
            System.out.println(task.getName());
        }
        System.out.println();
    }
}
