package managers;

import tasks.Task;

import java.util.LinkedList;
import java.util.List;

public interface HistoryManager {

    void add(Integer id);

    void remove(int id);

    List<Integer> getHistory();
}
