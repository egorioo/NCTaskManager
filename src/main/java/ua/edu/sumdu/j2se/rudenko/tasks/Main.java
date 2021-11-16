package ua.edu.sumdu.j2se.rudenko.tasks;
import java.util.Iterator;
public class Main {

    public static void main(String[] args) {
        AbstractTaskList list = new LinkedTaskList();

        Task task1 = new Task("A", 5);
        Task task2 = new Task("B", 5);
        Task task3 = new Task("C", 5);
        Task task4 = new Task("D", 5);

        /*LinkedTaskList list = new LinkedTaskList();*/
        list.add(task1);
        list.add(task2);
        list.add(task3);

        System.out.println(list.toString());
    }
}