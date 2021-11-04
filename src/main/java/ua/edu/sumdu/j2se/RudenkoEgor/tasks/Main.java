package ua.edu.sumdu.j2se.RudenkoEgor.tasks;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Task task = new Task("name", 2);
        AbstractTaskList list = new LinkedTaskList();
        list.add(task);
    }
}