package ua.edu.sumdu.j2se.RudenkoEgor.tasks;

public class ArrayTaskList {
    private int capacity = 10;
    private Task list[] = new Task[capacity];

    ArrayTaskList() {
        init(list);
    }

    private void init(Task[] list) {
        for (int i = 0; i < list.length; i++) {
            list[i] = null;
        }
    }

    private int getSize(Task[] list) {
        int size = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) size++;
        }
        return size;
    }

    private int getCapacity(Task[] list) {
        int capacity = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] == null) capacity++;
        }
        return capacity + getSize(list);
    }

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

    public int size() {
        return getSize(list);
    }

    public Task getTask(int index) {
        return list[index];
    }

    public ArrayTaskList incoming(int from, int to) {
        ArrayTaskList arr = new ArrayTaskList();
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) {
                if (list[i].isRepeated()) {
                    for (int j = list[i].getStartTime(); j < list[i].getEndTime(); j += list[i].getRepeatInterval()){

                        if (list[i].nextTimeAfter(j) > from && list[i].nextTimeAfter(j) < to) {
                            arr.add(list[i]);
                            break;
                        }
                    }
                }
                else {
                    if (list[i].getTime() > from && list[i].getTime() < to) {
                        arr.add(list[i]);
                    }

                }
            }
        }

        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.getTask(i).getTitle());
        }
        return arr;


    }
}
