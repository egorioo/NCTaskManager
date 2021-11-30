package ua.edu.sumdu.j2se.rudenko.tasks;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Main {
    public static void main(String[] args) {
        AbstractTaskList list = new ArrayTaskList();
        Task t1 = new Task("name", LocalDateTime.now(), LocalDateTime.now().plusDays(2),500);
        Task t2 = new Task("name", LocalDateTime.now());
        Task t3 = new Task("name", LocalDateTime.now());
        t1.setActive(true);
        list.add(t1);
        list.add(t2);
        list.add(t3);


        /*TaskIO.writeBinary(list,new File("file.txt"));

        System.out.println("----------------");*/
        AbstractTaskList arr = new ArrayTaskList();
        /*TaskIO.readBinary(arr,new File("file.txt"));

        System.out.println(arr);*/

        /*try {
           TaskIO.write(list, new FileWriter("file.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            TaskIO.read(arr, new FileReader("file.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}