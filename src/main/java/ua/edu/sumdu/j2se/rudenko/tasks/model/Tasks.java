package ua.edu.sumdu.j2se.rudenko.tasks.model;

import java.util.*;
import java.time.LocalDateTime;

public class Tasks {
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end)
            throws IllegalArgumentException, NullPointerException {
        if (start == null || end == null) {
            throw new NullPointerException();
        }
        if (start.isAfter(end) || start.isEqual(end)) {
            throw new IllegalArgumentException("Invalid parameters specified");
        }

        LinkedList<Task> list = new LinkedList<>();
        for (Task task : tasks) {
            if (task.nextTimeAfter(start) != null && task.nextTimeAfter(start).compareTo(end) <= 0) {
                list.add(task);
            }
        }
        return list;
    }

    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end)
            throws ClassNotFoundException {
        SortedMap<LocalDateTime, Set<Task>> list = new TreeMap<>();
        var arr = incoming(tasks, start, end);
        LocalDateTime nextTime;
        for (Task task : arr) {
            nextTime = start;
            while (true) {
                nextTime = task.nextTimeAfter(nextTime);
                if (nextTime == null || nextTime.isAfter(end)) {
                    break;
                }
                if (list.containsKey(nextTime)) {
                    list.get(nextTime).add(task);
                } else {
                    Set<Task> temp = new HashSet<>();
                    temp.add(task);
                    list.put(nextTime, temp);
                }
            }
        }
        return list;
    }
}
