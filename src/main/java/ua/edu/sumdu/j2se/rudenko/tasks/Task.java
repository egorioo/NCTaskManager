package ua.edu.sumdu.j2se.rudenko.tasks;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Cloneable {
    private String title;
    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;
    private int interval;
    private boolean active;
    private boolean repeat;

    /**
     * Constructor of an inactive task that runs at a given time once
     *
     * @param title - task name
     * @param time  - task execution time
     */
    public Task(String title, LocalDateTime time) {
        setTitle(title);
        setTime(time);
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
    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) {
        setTitle(title);
        setTime(start, end, interval);
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
    public LocalDateTime getTime() {
        if (repeat) return start;
        else return time;
    }

    /**
     * Changes the execution time of the task. If the task is repeated,
     * turns into one that does not repeat
     */
    public void setTime(LocalDateTime time) throws IllegalArgumentException {
        if (time == null) {
            throw new IllegalArgumentException("Time of the task cannot be null");
        }
        this.time = time;
        if (repeat) {
            start = time;
            end = time;
            interval = 0;
            repeat = false;
        }
    }

    /**
     * Returns the start time of the task. If the task is not repeated,
     * the execution time is returned
     */
    public LocalDateTime getStartTime() {
        if (repeat) return start;
        else return time;
    }

    /**
     * Returns the end of the task execution. If the task is not repeated,
     * the execution time is returned
     */
    public LocalDateTime getEndTime() {
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
    public void setTime(LocalDateTime start, LocalDateTime end, int interval) throws IllegalArgumentException, NullPointerException {
        if (start == null || end == null) {
            throw new NullPointerException();
        }
        if (start.isAfter(end) || start.isEqual(end) || interval <= 0) {
            throw new IllegalArgumentException("Invalid parameters specified");
        }
        this.start = start;
        this.end = end;
        this.interval = interval;

        if (!repeat) {
            repeat = true;
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
     * If the task does not run after the specified time, returns null
     */
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if (active && !isRepeated()) {
            if (current.isBefore(time)) {
                return time;
            }
            return null;
        }

        if (active) {
            LocalDateTime nextTime = start;
            if (current.isBefore(start)) {
                return start;
            } else if (current.isAfter(end)) {
                return null;
            } else {
                while (nextTime.isBefore(end) || nextTime.isEqual(end)) {
                    if (nextTime.isAfter(current)) {
                        return nextTime;
                    }
                    nextTime = nextTime.plusSeconds(interval);
                }
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), isActive(), getStartTime(), getEndTime(), getRepeatInterval());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task that = (Task) o;
        if (isActive() != that.isActive() ||
                isRepeated() != that.isRepeated() ||
                getTitle() != that.getTitle() ||
                getEndTime() != that.getEndTime() ||
                getTime() != that.getTime() ||
                getStartTime() != that.getStartTime() ||
                getRepeatInterval() != that.getRepeatInterval()) {
            return false;
        }
        return true;
    }

    @Override
    public Task clone() throws CloneNotSupportedException {
        Task task = new Task(this.title, this.time);
        task.active = this.active;
        task.end = this.end;
        task.interval = this.interval;
        task.repeat = this.repeat;
        task.start = this.start;
        return task;
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", active=" + active +
                ", repeat=" + repeat;
    }
}