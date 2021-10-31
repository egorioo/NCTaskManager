package ua.edu.sumdu.j2se.RudenkoEgor.tasks;

public class TaskListFactory {
    /**
     * Method that creates objects of two types.
     *
     * @param type if type = ARRAY create object of class ArrayTaskList
     *             if type = LINKED create object of class
     * @return object of selected class
     */
    public static AbstractTaskList createTaskList(ListTypes.types type) throws ClassNotFoundException {
        if (type == ListTypes.types.ARRAY) {
            return new ArrayTaskList();
        } else if (type == ListTypes.types.LINKED) {
            return new LinkedTaskList();
        }
        throw new ClassNotFoundException("This type is not supported");
    }
}
