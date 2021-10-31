package ua.edu.sumdu.j2se.RudenkoEgor.tasks;

public class LinkedTaskList extends AbstractTaskList{
    private int size;
    private Node head;
    private Node lastElem;

    /**
     * Inner class that represents one element of a linked list
     */
    private class Node {
        /**
         * @param pNext - pointer to the next item in the list
         */
        public Node pNext;
        /**
         * @param data - information
         */
        public Task data;

        public Node(Task data) {
            this.data = data;
        }
    }

    /**
     * Method that return size of list
     */
    public int size() {
        return size;
    }

    /**
     * Method that adds an element to the end
     */
    public void add(Task task) {
        /** If the list is empty, create an element*/
        if (head == null) {
            head = new Node(task);
        }
        /** If there is only one element in the list*/
        else if (lastElem == null) {
            Node current = head;
            while (current.pNext != null) {
                current = current.pNext;
            }
            current.pNext = new Node(task);
            lastElem = current.pNext;
        }
        /** If there is more than one item in the list.
         * Items are added immediately to the end through a pointer to the end of the list*/
        else {
            Node temp = lastElem;
            temp.pNext = new Node(task);
            lastElem = temp.pNext;
        }
        size++;
    }

    /**
     * Method returns the element at its index in the list
     */
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

    /**
     * Method that removes a task from the list
     *
     * @return true - if element is found and removed, false - if element is not found
     */
    public boolean remove(Task task) {
        Node current = head;
        Node next = head.pNext;
        int counter = 0;
        /** If there is one element in the list*/
        if (next == null) {
            if (current.data == task) {
                head = null;
                size--;
                return true;
            }
            return false;
        }
        /** If 0 item has a task to be deleted*/
        if (current.data == task) {
            Node temp = head;
            head = head.pNext;
            temp = null;
            size--;
            return true;
        }
        /** Loops through all the elements until the required task is found*/
        while (next != null) {

            if (next.pNext == null && next.data == task) {
                current.pNext = next.pNext;
                lastElem = current;
                size--;
                return true;
            }
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
}
