package ua.edu.sumdu.j2se.rudenko.tasks;

import java.time.LocalDateTime;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        Task ex1 = new Task("name", LocalDateTime.of(2021, 11, 20, 12, 00), LocalDateTime.of(2021, 11, 21, 12, 00), 5);
        ex1.setActive(true);
        System.out.println(ex1.nextTimeAfter(LocalDateTime.of(2021, 11, 20, 12, 00)));
    }
}