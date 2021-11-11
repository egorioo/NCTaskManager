package ua.edu.sumdu.j2se.rudenko.tasks;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayTaskList extends AbstractTaskList {
    private final int capacity = 10;
    private Task list[] = new Task[capacity];

    /**
     * Constructor where all elements are initialized to null
     */
    public ArrayTaskList() {
        init(list);
    }

    /**
     * Private method that fills the array with null
     *
     * @param list - the array to be filled
     */
    private void init(Task[] list) {
        for (int i = 0; i < list.length; i++) {
            list[i] = null;
        }
    }

    /**
     * Private method that returns the size of the array
     *
     * @param list - the array to be sized
     */
    private int getSize(Task[] list) {
        int size = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) size++;
        }
        return size;
    }

    /**
     * Private method that returns the capacity of the array
     */
    private int getCapacity(Task[] list) {
        int capacity = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] == null) capacity++;
        }
        return capacity + getSize(list);
    }

    /**
     * Method for adding a task to an array
     *
     * @param task - task to add
     */
    public void add(Task task) {
        if (getCapacity(list) >= getSize(list) * 1.5) {
            for (int i = 1; i < list.length; i++) {
                if (list[0] == null) {
                    list[0] = task;
                    break;
                } else if (list[i] == null) {
                    list[i] = task;
                    break;
                }
            }
        } else {
            Task list2[] = new Task[(int) (getSize(list) * 2)];
            for (int i = 0; i < list.length; i++) {
                list2[i] = list[i];
            }
            list = list2;
            for (int i = 1; i < list.length; i++) {
                if (list[0] == null) {
                    list[0] = task;
                    break;
                } else if (list[i] == null) {
                    list[i] = task;
                    break;
                }
            }
        }
    }

    /**
     * Method for removing a task from an array
     *
     * @param task - task to remove
     */
    public boolean remove(Task task) {
        for (int i = 0; i < list.length; i++) {
            if (list[i] == task) {
                Task listTemp[] = new Task[getCapacity(list) - 1];
                int j = 0;
                for (; j < i; j++) {
                    listTemp[j] = list[j];
                }
                j++;
                for (; j < list.length; j++) {
                    int temp = j;
                    listTemp[--temp] = list[j];
                }
                list = listTemp;
                return true;
            }
        }
        return false;
    }

    /**
     * Method that returns the size of the array
     */
    public int size() {
        return getSize(list);
    }

    /**
     * Method that returns an element of an array by index
     */
    public Task getTask(int index) {
        return list[index];
    }

    public ListTypes.types getType() {
        return ListTypes.types.ARRAY;
    }

    /**
     * Implementing an iterator for a ArrayTaskList
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
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public Task next() {
                if (index == size()) {
                    throw new NoSuchElementException("Iterator has reached the last item");
                }
                return list[index++];
            }

            @Override
            public void remove() {
                if (index == 0) {
                    throw new IllegalStateException("First you need to call the next() method");
                }
                index--;
                Task listTemp[] = new Task[size() - 1];
                System.arraycopy(list, 0, listTemp, 0, index);
                System.arraycopy(list, ++index, listTemp, --index, size() - (++index));
                list = listTemp;
                index--;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayTaskList that = (ArrayTaskList) o;
        for (int i = 0; i < ((ArrayTaskList) o).size(); i++) {
            if (list[i].isActive() != that.getTask(i).isActive() ||
                    list[i].isRepeated() != that.getTask(i).isRepeated() ||
                    list[i].getTitle() != that.getTask(i).getTitle() ||
                    list[i].getEndTime() != that.getTask(i).getEndTime() ||
                    list[i].getTime() != that.getTask(i).getTime() ||
                    list[i].getStartTime() != that.getTask(i).getStartTime() ||
                    list[i].getRepeatInterval() != that.getTask(i).getRepeatInterval()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < getSize(list); i++) {
            result += list[i].hashCode();
        }
        return result;
    }

    @Override
    public ArrayTaskList clone() {
        ArrayTaskList retObj = new ArrayTaskList();
        for(int counter = 0; counter < size(); counter++) {
            retObj.add(list[counter]);
        }
        return retObj;
    }

    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "capacity=" + capacity +
                ", list=" + Arrays.toString(list) +
                '}';
    }
}