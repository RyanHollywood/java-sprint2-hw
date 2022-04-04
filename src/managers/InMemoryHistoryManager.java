package managers;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    //Реализуем собственный двусвязный список
    private Node<Integer> head;
    private Node<Integer> tail;

    //HashMap для удаления Node за O(1)

    private Map<Integer, Node> historyMap;

    //Конструктор
    public InMemoryHistoryManager() {
        historyMap = new HashMap<>();
    }

    public void linkLast(Integer id) {
        Node<Integer> oldTail = tail;
        Node<Integer> newTail = new Node<Integer>(oldTail, id, null);
        tail = newTail;
        if (oldTail == null) {
            head = newTail;
        } else {
            oldTail.next = newTail;
        }
    }

    private void removeNode(Node<Integer> node) {
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

    private ArrayList<Integer> getTasks() {
        ArrayList<Integer> tasksList = new ArrayList<>();
        tasksList.add(head.data);
        Node<Integer> iteratorNode = head.next;
        while (iteratorNode != null) {
            tasksList.add(iteratorNode.data);
            iteratorNode = iteratorNode.next;
        }
        return tasksList;
    }

    @Override
    public void add(Integer id) {
        if (historyMap.containsKey(id) & historyMap.size() > 1) {
            removeNode(historyMap.get(id));
        }
        linkLast(id);
        historyMap.put(id, tail);
    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            removeNode(historyMap.get(id));
            historyMap.remove(id);
        }
    }

    @Override
    public List<Integer> getHistory() {
        return new LinkedList<>(getTasks());
    }

    public static String toString(HistoryManager manager) {
        String[] historyArray = new String[manager.getHistory().size()];
        for (int i = 0; i < manager.getHistory().size(); i++) {
            historyArray[i] = manager.getHistory().get(i).toString();
        }
        return String.join(",", historyArray);
    }

    public static List<Integer> fromString(String historyString) {
        List<Integer> history = new ArrayList<>();
        String[] historyArray = historyString.split(",");
        for (int i = 0; i < historyArray.length; i++) {
            history.add(Integer.parseInt(historyArray[i]));
        }
        return history;
    }
}
