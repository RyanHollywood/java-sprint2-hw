package managers;

import exception.ManagerSaveException;
import tasks.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTasksManager {

    String historyURI;

    public FileBackedTasksManager(String historyURI) {
        super();
        this.historyURI = historyURI;
    }

    @Override
    public Task getTaskById(Integer id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(Integer id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        Subtask subtask = super.getSubtaskById(id);
        save();
        return subtask;
    }

    private String createHeader() {
            return "id,type,name,status,description,epic\n";
        }

    private List<Task> getAll() {
        List<Task> allTasks = new ArrayList<>();
        if (super.getAllTasks() != null) {
            for (Task task : super.getAllTasks()) {
                allTasks.add(task);
            }
        }
        if (super.getAllEpics() != null) {
            for (Task epic : super.getAllEpics()) {
                allTasks.add(epic);
            }
        }
        if (super.getAllSubtasks() != null) {
            for (Task subtask : super.getAllSubtasks()) {
                allTasks.add(subtask);
            }
        }
        TaskIdComparator comparator = new TaskIdComparator();
        allTasks.sort(comparator);
        return allTasks;
    }

    private String createTasksBlock() {
        String tasksBlock = "";
        for (Task task : getAll()) {
            tasksBlock = tasksBlock + task.toString() + "\n";
        }
        return tasksBlock;
    }

    private void save() {
        try (Writer fileWriter = new FileWriter(historyURI)) {
            fileWriter.write(createHeader());
            fileWriter.write(createTasksBlock());
            fileWriter.write("\n");
            fileWriter.write(InMemoryHistoryManager.toString(inMemoryHistoryManager));
        } catch (IOException e) {
            throw new ManagerSaveException("ManagerSaveException");
        }
    }

    private static FileBackedTasksManager readTasksBlock(ArrayList<String> fileLines, FileBackedTasksManager tasksManager) {
        for (int i = 1; i < fileLines.size() - 2; i++) {
            String[] taskLine = fileLines.get(i).split(",");
            if (taskLine[1].equals("TASK")) {
                tasksManager.createTask(Task.fromString(fileLines.get(i)));
            } else if (taskLine[1].equals("EPIC")) {
                tasksManager.createEpic(Epic.fromString(fileLines.get(i)));
            } else {
                tasksManager.createSubtask(Subtask.fromString(fileLines.get(i)));
            }
        }
        return tasksManager;
    }

    private static FileBackedTasksManager fillHistory(ArrayList<String> fileLines, FileBackedTasksManager tasksManager) {
        String[] history = fileLines.get(fileLines.size()-1).split(",");
        for (int i = 0; i < history.length; i++) {
            Integer id = Integer.parseInt(history[i]);
            if (tasksManager.getTypeById(id).equals(TaskType.TASK)) {
                tasksManager.getTaskById(id);
            } else if (tasksManager.getTypeById(id).equals(TaskType.EPIC)) {
                tasksManager.getEpicById(id);
            } else {
                tasksManager.getSubtaskById(id);
            }
        }
        return tasksManager;
    }

    public static FileBackedTasksManager loadFromFile(String historyURI) {
        FileBackedTasksManager tasksManager = new FileBackedTasksManager("history.txt");
        ArrayList<String> fileLines = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(historyURI))) {
            while (fileReader.ready()) {
                String line = fileReader.readLine();
                fileLines.add(line);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("ManagerSaveException");
        }
        tasksManager = readTasksBlock(fileLines, tasksManager);
        tasksManager = fillHistory(fileLines, tasksManager);
        return tasksManager;
    }

    //Тестирование
    public static void main(String[] args) {

        FileBackedTasksManager tasksManager = new FileBackedTasksManager("history.txt");

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
    }
}

//Компаратор для сортировки задач по id для записи в файл
class TaskIdComparator implements Comparator<Task> {
    @Override
    public int compare(Task task1, Task task2) {
        if (task1.getId() > task2.getId()) {
            return 1;
        } else {
            return -1;
        }
    }
}


