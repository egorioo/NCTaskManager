package ua.edu.sumdu.j2se.rudenko.tasks.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedTaskList extends AbstractTaskList {
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
        /** If there is one element in the list*/
        if (next == null) {
            if (current.data == task) {
                head = null;
                size--;
                lastElem = null;
                return true;
            }
            return false;
        }
        /** If 0 item has a task to be deleted*/
        if (current.data == task) {
            head = head.pNext;
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

    public ListTypes.types getType() {
        return ListTypes.types.LINKED;
    }

    /**
     * Implementing an iterator for a LinkedTaskList
     * <p>
     * next() - by calling the next() method, you can get the next element
     *
     * @return true - if there are items,
     * false - if there are no elements, or the end is reached
     * <p>
     * remove() - removes an item from the collection
     * @throws NoSuchElementException when the end of the collection is reached
     *                                <p>
     *                                hasNext() - using the hasNext() method, you can find out if there is a next element
     *                                and if the end of the collection has been reached.
     * @throws IllegalStateException  - if before calling remove() method,
     *                                you haven't called the method next()
     */
    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            private Node current = head;
            private Node prev;
            private boolean isFirst = true;
            private boolean deletedFirst;

            @Override
            public boolean hasNext() {
                return current.pNext != null;
            }

            @Override
            public Task next() {
                if (current.pNext == null && !deletedFirst) {
                    throw new NoSuchElementException("Iterator has reached the last item");
                }
                /**If this is the first item*/
                if (isFirst) {
                    isFirst = false;
                    return current.data;
                }
                /**If this is the first item, it is removed*/
                if (deletedFirst) {
                    current = prev;
                    prev = null;
                    deletedFirst = false;
                    return current.data;
                }
                prev = current;
                current = current.pNext;
                return current.data;
            }

            @Override
            public void remove() {
                if (isFirst) {
                    throw new IllegalStateException("First you need to call the next() method");
                }
                /**If you need to delete a single element*/
                if (current == head && current.pNext == null) {
                    current.data = null;
                    size--;
                    /**If you need to remove the first element*/
                } else if (current == head && current.pNext != null) {
                    prev = current.pNext;
                    head = head.pNext;
                    current.pNext = null;
                    current.data = null;
                    deletedFirst = true;
                    size--;
                } else {
                    var temp = current.pNext;
                    prev.pNext = temp;
                    current = prev;
                    size--;
                }
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedTaskList that = (LinkedTaskList) o;
        Node runner = head;
        Node givenobj = that.head;
        if (runner == null && givenobj == null) {
            return true;
        }
        while (runner != null) {
            if (!runner.data.equals(givenobj.data)) {
                return false;
            }
            runner = runner.pNext;
            givenobj = givenobj.pNext;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        Node runner = head;
        while (runner != null) {
            result += runner.data.hashCode();
            runner = runner.pNext;
        }
        return result;
    }

    @Override
    public LinkedTaskList clone() {
        LinkedTaskList returnObj = new LinkedTaskList();
        Node toAdd = head;

        while (toAdd != null) {
            returnObj.add(toAdd.data);
            toAdd = toAdd.pNext;
        }
        return returnObj;
    }

}