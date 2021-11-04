package ua.edu.sumdu.j2se.RudenkoEgor.tasks;

/**
 * Abstract class that describes the functionality of
 * the classes ArrayTaskList and LinkedTaskList
 */
public abstract class AbstractTaskList {
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
}
