package ua.edu.sumdu.j2se.rudenko.tasks;

import java.util.ArrayList;
import java.util.Objects;

public class Task {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean active;
    private boolean repeat;

    /**
     * Constructor of an inactive task that runs at a given time once
     *
     * @param title - task name
     * @param time  - task execution time
     */
    public Task(String title, int time) throws IllegalArgumentException {
        if (title == null) {
            throw new IllegalArgumentException("Tittle of the task can't be null");
        }
        this.title = title;
        if (time < 0) {
            throw new IllegalArgumentException("Time cannot be less than zero");
        }
        this.time = time;
    }

    /**
     * Constructor of an inactive task that is
     * specified by a time interval and runs at an interval
     *
     * @param title    - task name
     * @param start    - start time
     * @param end      - end time
     * @param interval - time interval after which the task will be repeated
     */
    public Task(String title, int start, int end, int interval) throws IllegalArgumentException {
        if (title == null) {
            throw new IllegalArgumentException("Title of the task cannot be null");
        }
        this.title = title;
        if (start < 0) {
            throw new IllegalArgumentException("Start point cannot be less than zero");
        }
        this.start = start;
        if (end < 0) {
            throw new IllegalArgumentException("End point cannot be less than zero");
        } else if (start >= end) {
            throw new IllegalArgumentException("End point cannot be less or equal to start point");
        }
        this.end = end;
        if (interval < 0) {
            throw new IllegalArgumentException("Interval cannot be less than zero");
        }
        this.interval = interval;
        this.repeat = true;
    }

    /**
     * Returns the name of the task
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the name of the task
     */
    public void setTitle(String title) throws IllegalArgumentException {
        if (title == null) {
            throw new IllegalArgumentException("Title of the task cannot be null");
        }
        this.title = title;
    }

    /**
     * Method for checking the status of a task
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Method for setting task state
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the execution time of the task, or the start time for a task that is repeating
     */
    public int getTime() {
        if (repeat) return start;
        else return time;
    }

    /**
     * Changes the execution time of the task. If the task is repeated,
     * turns into one that does not repeat
     */
    public void setTime(int time) throws IllegalArgumentException {
        if (time < 0) {
            throw new IllegalArgumentException("Time cannot be less than zero");
        }
        this.time = time;
        if (repeat) {
            start = 0;
            end = 0;
            interval = 0;
            repeat = false;
        }
    }

    /**
     * Returns the start time of the task. If the task is not repeated,
     * the execution time is returned
     */
    public int getStartTime() {
        if (repeat) return start;
        else return time;
    }

    /**
     * Returns the end of the task execution. If the task is not repeated,
     * the execution time is returned
     */
    public int getEndTime() {
        if (repeat) return end;
        else return time;
    }

    /**
     * Returns the interval. Returns 0 if the task is not repeated
     */
    public int getRepeatInterval() {
        if (repeat) return interval;
        else return 0;
    }

    /**
     * A method that changes the start time, end time and repetition interval of a task.
     * If the task is not repetitive, it turns into a repetitive one
     */
    public void setTime(int start, int end, int interval) throws IllegalArgumentException {
        if (start < 0) {
            throw new IllegalArgumentException("Start point cannot be less than zero");
        }
        if (end < 0) {
            throw new IllegalArgumentException("End point cannot be less than zero");
        } else if (start >= end) {
            throw new IllegalArgumentException("End point cannot be less or equal to start point");
        }
        if (interval < 0) {
            throw new IllegalArgumentException("Interval cannot be less than zero");
        }
        this.start = start;
        this.end = end;
        this.interval = interval;
        if (!repeat) {
            repeat = true;
            time = 0;
        }
    }

    /**
     * Checks the status of the task, whether it is repeating or not
     */
    public boolean isRepeated() {
        return repeat;
    }

    /**
     * Method that returns the time of the next task execution after the specified time
     * If the task does not run after the specified time, returns -1
     */
    public int nextTimeAfter(int current) {
        if (!active) return -1;
        if (!repeat) {
            return (current >= time) ? -1 : time;
        }

        if (current < start) return start;
        if (current >= end) return -1;

        ArrayList<Integer> nextTime = new ArrayList<Integer>();
        for (int j = start; j < end; j += interval) {
            nextTime.add(j);
        }

        for (int i = 0; i < nextTime.size(); i++) {
            if (current == nextTime.get(i)) {
                if (current == nextTime.get(nextTime.size() - 1)) {
                    return -1;
                } else return nextTime.get(i + 1);
            }
        }

        for (int i = 0; i < nextTime.size(); i++) {
            if (current >= nextTime.get(nextTime.size() - 1) && current < end) {
                return -1;
            }

            if (current > nextTime.get(i) && current < nextTime.get(i + 1)) {
                return nextTime.get(i + 1);
            }
        }

        return -1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(),isActive(),getStartTime(),getEndTime(),getRepeatInterval());
    }
}