package managers;

import tasks.Task;

import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {

    final static int HISTORY_SIZE = 10;

    private LinkedList<Task> history;

    public InMemoryHistoryManager() {
        history = new LinkedList<>();
    }

    @Override
    public void add(Task task) {
        if (history.size() >= HISTORY_SIZE) {
            history.remove(0);
        }
        history.add(task);
    }


    @Override
    public LinkedList<Task> getHistory() {
        return history;
    }
}
