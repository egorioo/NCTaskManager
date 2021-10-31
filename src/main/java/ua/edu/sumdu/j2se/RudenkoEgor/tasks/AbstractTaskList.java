package ua.edu.sumdu.j2se.RudenkoEgor.tasks;

/**
 * Abstract class that describes the functionality of
 * the classes ArrayTaskList and LinkedTaskList
 */
abstract class AbstractTaskList {
    abstract public void add(Task task);

    abstract int size();

    abstract public Task getTask(int index);

    abstract boolean remove(Task task);

    /**
     * Method that returns a subset of tasks that are scheduled
     * to run at least once after time 'from' and no later than 'to'.
     *
     * @param from - start time
     * @param to   - end time
     */
    public AbstractTaskList incoming(int from, int to) {
        AbstractTaskList list;
        if (this.getClass().getSimpleName().equals("ArrayTaskList")) {
            list = new ArrayTaskList();
        } else {
            list = new LinkedTaskList();
        }
        for (int i = 0; i < list.size(); i++) {
            if (getTask(i).nextTimeAfter(from) < to && getTask(i).nextTimeAfter(from) != -1) {
                list.add(getTask(i));
            }
        }
        return list;
    }
}
