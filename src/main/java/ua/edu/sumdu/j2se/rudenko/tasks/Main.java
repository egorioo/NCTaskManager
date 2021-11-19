package ua.edu.sumdu.j2se.rudenko.tasks;
import java.util.Iterator;
public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        AbstractTaskList arr3 = new ArrayTaskList();

        Task ex1 = new Task("1", 9);
        Task ex2 = new Task("2", 15,25,5);
        Task ex3 = new Task("3", 26);
        Task ex4 = new Task("4", 10,14,2);

        ex1.setActive(true);
        ex2.setActive(true);
        ex3.setActive(true);
        ex4.setActive(true);

        arr3.add(ex1);
        arr3.add(ex2);
        arr3.add(ex3);
        arr3.add(ex4);

        AbstractTaskList list = arr3.incoming(11,21);

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.getTask(i).getTitle());
        }
    }
}