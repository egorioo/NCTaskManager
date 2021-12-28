package ua.edu.sumdu.j2se.rudenko.tasks.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Abstract class that describes the functionality of
 * the classes ArrayTaskList and LinkedTaskList
 */
public abstract class AbstractTaskList implements Iterable<Task>, Cloneable {
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
    final public AbstractTaskList incoming(LocalDateTime from, LocalDateTime to) throws IllegalArgumentException, ClassNotFoundException {
        if (from == null || to == null || from.isAfter(to))
            throw new IllegalArgumentException("Invalid parameters specified");

        AbstractTaskList list = TaskListFactory.createTaskList(getType());

        getStream().filter(e -> e.nextTimeAfter(from).isAfter(from) && e.nextTimeAfter(to).isBefore(to)).forEach(list::add);
        return list;
    }

    public Stream<Task> getStream() {
        Task[] task = new Task[size()];

        for (int i = 0; i < size(); i++) {
            task[i] = getTask(i);
        }
        return Arrays.stream(task);
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
        while (it.hasNext()) {
            str.append(" Elem №" + counter + " {");
            str.append(it.next().toString());
            str.append("}");
            counter++;
        }
        return new String(str);
    }
}