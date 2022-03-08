package managers;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    //Реализуем собственный двусвязный список
    private Node<Task> head;
    private Node<Task> tail;

    //HashMap для удаления Node за O(1)

    private Map<Integer, Node> historyMap;

    //Конструктор
    public InMemoryHistoryManager() {
        historyMap = new HashMap<>();
    }

    public void linkLast(Task task) {
        Node<Task> oldTail = tail;
        Node<Task> newTail = new Node<>(oldTail, task, null);
        tail = newTail;
        if (oldTail == null) {
            head = newTail;
        } else {
            oldTail.next = newTail;
        }
    }

    private void removeNode(Node<Task> node) {
        if (node.prev == null) {
            node.next.prev = null;
            head = node.next;
        } else if (node.next == null) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> tasksList = new ArrayList<>();
        tasksList.add(head.data);
        Node<Task> iteratorNode = head.next;
        while (iteratorNode != null) {
            tasksList.add(iteratorNode.data);
            iteratorNode = iteratorNode.next;
        }
        return tasksList;
    }

    @Override
    public void add(Task task) {
        if (historyMap.containsKey(task.getId())) {
            removeNode(historyMap.get(task.getId()));
        }
        linkLast(task);
        historyMap.put(task.getId(), tail);
    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            removeNode(historyMap.get(id));
            historyMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return new LinkedList<>(getTasks());
    }
}
