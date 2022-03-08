import managers.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        //Тестирование
        //Две простые задачи
        Task task1 = new Task("Task1", "Task1-description", taskManager.getNewId(), "NEW");
        taskManager.createTask(task1);

        Task task2 = new Task("Task2", "Task2-description", taskManager.getNewId(), "NEW");
        taskManager.createTask(task2);

        //Эпик с одной подзадачей
        Epic epic1 = new Epic("Epic1", "Epic1-description", taskManager.getNewId());
        taskManager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Subtask1", "Subtask1 description", taskManager.getNewId(), "NEW", epic1);
        taskManager.createSubtask(subtask1);

        //Эпик с двумя подзадачами
        Epic epic2 = new Epic("Epic2", "Epic2-description", taskManager.getNewId());
        taskManager.createEpic(epic2);

        Subtask subtask2 = new Subtask("Subtask2", "Subtask2-description", taskManager.getNewId(), "NEW", epic2);
        taskManager.createSubtask(subtask2);

        Subtask subtask3 = new Subtask("Subtask3", "Subtask3-description", taskManager.getNewId(), "NEW", epic2);
        taskManager.createSubtask(subtask3);

        //Вывод всех задач, эпиков и подзадач
        printAllTasks(taskManager);

        //Обновление задачи
        Task task1update = new Task("Task1", "Task1-description", task1.getId(), "IN_PROGRESS");
        taskManager.updateTask(task1update);

        //Обновление подзадачи
        Subtask subtask2update = new Subtask("Subtask2", "Subtask3-description", subtask2.getId(), "NEW", epic2);
        taskManager.updateSubtask(subtask2update);

        Subtask subtask3update = new Subtask("Subtask3", "Subtask3-description", subtask3.getId(), "DONE", epic2);
        taskManager.updateSubtask(subtask3update);

        printAllTasks(taskManager);

        taskManager.deleteTaskById(task1.getId());
        taskManager.deleteEpicById(epic1.getId());

        printAllTasks(taskManager);
        printSubtasksOfEpic(taskManager, epic2);

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
}
