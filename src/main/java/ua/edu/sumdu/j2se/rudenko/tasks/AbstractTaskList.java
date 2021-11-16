package ua.edu.sumdu.j2se.rudenko.tasks;

import java.util.Iterator;

/**
 * Abstract class that describes the functionality of
 * the classes ArrayTaskList and LinkedTaskList
 */
public abstract class AbstractTaskList implements Iterable, Cloneable {
    public abstract void add(Task task);

    public abstract int size();

    public abstract Task getTask(int index);

    public abstract boolean remove(Task task);

    public abstract ListTypes.types getType();

    /**
     * Method that returns a subset of tasks that are scheduled
     * to run at least once after time 'from' and no later than 'to'.
     *
     * @param from - start time
     * @param to   - end time
     */
    public AbstractTaskList incoming(int from, int to) throws IllegalArgumentException, ClassNotFoundException {
        if (from < 0 || to <= from) throw new IllegalArgumentException("Invalid parameters specified");

        AbstractTaskList list = TaskListFactory.createTaskList(getType());

        for (int i = 0; i < list.size(); i++) {
            if (getTask(i).nextTimeAfter(from) < to && getTask(i).nextTimeAfter(from) != -1) {
                list.add(getTask(i));
            }
        }
        return list;
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();
        if (getType() == ListTypes.types.ARRAY)
            str.append("ArrayTaskList | ");
        else str.append("LinkedTaskList | ");

        str.append("Size " + size() + " |");
        Iterator<Task> it = this.iterator();
        int counter = 0;
        while(it.hasNext()) {
            str.append(" Elem №" + counter + " {");
            str.append(it.next().toString());
            str.append("}");
            counter++;
        }
        return new String(str);
    }
}