import managers.Managers;
import managers.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        TaskManager inMemoryTaskManager = Managers.getDefault();

        //Тестирование
        //Две простые задачи
        Task task1 = new Task("Task1", "Task1-description", inMemoryTaskManager.getNewId(), TaskStatus.NEW);
        inMemoryTaskManager.createTask(task1);

        Task task2 = new Task("Task2", "Task2-description", inMemoryTaskManager.getNewId(), TaskStatus.NEW);
        inMemoryTaskManager.createTask(task2);

        //Эпик с одной подзадачей
        Epic epic1 = new Epic("Epic1", "Epic1-description", inMemoryTaskManager.getNewId());
        inMemoryTaskManager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Subtask1", "Subtask1 description", inMemoryTaskManager.getNewId(), TaskStatus.NEW, epic1);
        inMemoryTaskManager.createSubtask(subtask1);

        //Эпик с двумя подзадачами
        Epic epic2 = new Epic("Epic2", "Epic2-description", inMemoryTaskManager.getNewId());
        inMemoryTaskManager.createEpic(epic2);

        Subtask subtask2 = new Subtask("Subtask2", "Subtask2-description", inMemoryTaskManager.getNewId(), TaskStatus.NEW, epic2);
        inMemoryTaskManager.createSubtask(subtask2);

        Subtask subtask3 = new Subtask("Subtask3", "Subtask3-description", inMemoryTaskManager.getNewId(), TaskStatus.NEW, epic2);
        inMemoryTaskManager.createSubtask(subtask3);

        //Вывод всех задач, эпиков и подзадач
        printAllTasks(inMemoryTaskManager);

        //Обновление задачи
        Task task1update = new Task("Task1", "Task1-description", task1.getId(), TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.updateTask(task1update);

        //Обновление подзадачи
        Subtask subtask2update = new Subtask("Subtask2", "Subtask3-description", subtask2.getId(), TaskStatus.NEW, epic2);
        inMemoryTaskManager.updateSubtask(subtask2update);

        Subtask subtask3update = new Subtask("Subtask3", "Subtask3-description", subtask3.getId(), TaskStatus.DONE, epic2);
        inMemoryTaskManager.updateSubtask(subtask3update);

        //Вывод всех задач, эпиков и подзадач
        printAllTasks(inMemoryTaskManager);

        //Удаление задач по идентификатору
        inMemoryTaskManager.deleteTaskById(task1.getId());
        inMemoryTaskManager.deleteEpicById(epic1.getId());

        //Вывод всех задач, эпиков и подзадач
        printAllTasks(inMemoryTaskManager);

        //Вывод подзадач определенного эпика
        printSubtasksOfEpic(inMemoryTaskManager, epic2);

        //Заполнение истории просмотров
        for (int i = 0; i < 10; i++) {
            inMemoryTaskManager.getTaskById(task2.getId());
        }

        //Вывод истории просмотров
        printHistory(inMemoryTaskManager);

        System.out.println();

        //11ый просмотр
        inMemoryTaskManager.getEpicById(epic2.getId());

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

    //?
    static void printHistory(TaskManager inMemoryTaskManager) {
        LinkedList<Task> history = inMemoryTaskManager.history();
        for (Task task : history) {
            System.out.println(task.getName());
        }
    }
}
