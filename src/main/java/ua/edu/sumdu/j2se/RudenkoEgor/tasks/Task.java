package ua.edu.sumdu.j2se.RudenkoEgor.tasks;
import java.util.ArrayList;

public class Task {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean active;
    private boolean repeat;

    public Task(String title,int time) {
        this.title = title;
        this.time = time;
        this.repeat = false;
    }

    public Task(String title, int start, int end, int interval) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.repeat = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public int getTime(){
        if (repeat) return start;
        else return time;
    }

    public void setTime(int time){
        this.time = time;
        if (repeat) {
            start = 0;
            end = 0;
            interval = 0;
            repeat = false;
        }
    }

    public int getStartTime(){
        if (repeat) return start;
        else return time;
    }

    public int getEndTime(){
        if (repeat) return end;
        else return time;
    }

    public int getRepeatInterval(){
        if (repeat) return interval;
        else return 0;
    }

    public void setTime(int start, int end, int interval) {
        this.start = start;
        this.end = end;
        this.interval = interval;
        if (!repeat) {
            repeat = true;
            time = 0;
        }
    }

    public boolean isRepeated(){
        return repeat;
    }


    public int nextTimeAfter(int current) {
        if (!active) return -1;
        if (!repeat) {
            return (current >= time) ? -1 : time;
        }

        if (current < start ) return start;
        if (current >= end) return -1;

        ArrayList<Integer> NextTime = new ArrayList<Integer>();
        for(int j = start; j < end; j += interval) {
            NextTime.add(j);
        }

        for (int i = 0; i < NextTime.size(); i++) {
            if (current == NextTime.get(i)) {
                if (current == NextTime.get(NextTime.size() - 1)) {
                    return -1;
                }
                else return NextTime.get(i + 1);
            }
        }

        for (int i = 0; i < NextTime.size(); i++) {
            if (current >= NextTime.get(NextTime.size() - 1) && current < end) {
                return -1;
            }

            if (current > NextTime.get(i) && current < NextTime.get(i + 1)) {
                return NextTime.get(i+1);
            }
        }

        return -1;
    }

}