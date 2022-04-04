package managers;

public class Node<Integer> {
    public Integer data;
    public Node<Integer> next;
    public Node<Integer> prev;

    public Node(Node<Integer> prev, Integer data, Node<Integer> next) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    public Integer getData() {
        return data;
    }
}