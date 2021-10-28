package ua.edu.sumdu.j2se.RudenkoEgor.tasks;

public class LinkedTaskList {
    private int size;
    private Node head;

    private class Node {
        public Node pNext;
        public Task data;

        public Node(Task data) {
            this.data = data;
        }
    }

    public int size() {
        return size;
    }

    public void add(Task task) {
        if (head == null) {
            head = new Node(task);
        } else {
            Node current = head;
            while (current.pNext != null) {
                current = current.pNext;
            }
            current.pNext = new Node(task);
        }
        size++;
    }

    public Task getTask(int index) {
        int counter = 0;
        Node current = head;
        while (current != null) {
            if (counter == index) {
                return current.data;
            }
            current = current.pNext;
            counter++;
        }
        return null;
    }

    public boolean remove(Task task) {
        Node current = head;
        Node next = head.pNext;
        int counter = 0;
        if (next == null) {
            if (current.data == task) {
                head = null;
                size--;
                return true;
            }
            return false;
        }
        if (current.data == task) {
            Node temp = head;
            head = head.pNext;
            temp = null;
            size--;
            return true;
        }
        while (next != null) {
            if (next.data == task) {
                current.pNext = next.pNext;
                size--;
                return true;
            }
            current = current.pNext;
            next = next.pNext;
        }
        return false;
    }

    public LinkedTaskList incoming(int from, int to) {
        Node current = head;
        LinkedTaskList list = new LinkedTaskList();
        while (current != null) {
            if (current.data.nextTimeAfter(from) < to && current.data.nextTimeAfter(from) != -1) {
                list.add(current.data);
            }
            current = current.pNext;
        }
        return list;
    }
}
