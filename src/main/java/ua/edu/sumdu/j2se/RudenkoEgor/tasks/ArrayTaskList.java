package ua.edu.sumdu.j2se.RudenkoEgor.tasks;

public class ArrayTaskList {
    private int capacity = 10;
    private Task list[] = new Task[capacity];

    /**
     * Constructor where all elements are initialized to null
     */
    ArrayTaskList() {
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
                Task list2[] = new Task[getCapacity(list) - 1];
                int j = 0;
                for (; j < i; j++) {
                    list2[j] = list[j];
                }
                j++;
                for (; j < list.length; j++) {
                    int temp = j;
                    list2[--temp] = list[j];
                }
                list = list2;
                return true;
            }
        }
        return false;
    }

   /* public void print() {
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) {
                System.out.println(list[i].getTitle());
            }
        }
    }*/

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

    /**
     * Method that returns a subset of tasks that are scheduled
     * to run at least once after time 'from' and no later than 'to'.
     *
     * @param from - start time
     * @param to   - end time
     */
    public ArrayTaskList incoming(int from, int to) {
        ArrayTaskList arr = new ArrayTaskList();
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) {
                if (list[i].isRepeated()) {
                    for (int j = list[i].getStartTime(); j < list[i].getEndTime(); j += list[i].getRepeatInterval()) {
                        if (list[i].nextTimeAfter(j) > from && list[i].nextTimeAfter(j) < to) {
                            arr.add(list[i]);
                            break;
                        }
                    }
                } else {
                    if (list[i].getTime() > from && list[i].getTime() < to) {
                        arr.add(list[i]);
                    }
                }
            }
        }
        //test
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.getTask(i).getTitle());
        }
        //test
        return arr;
    }
}
