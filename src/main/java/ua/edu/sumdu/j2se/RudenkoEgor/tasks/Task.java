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

    /*Конструктор неактивной задачи которая выполняется один раз  */
    public Task(String title,int time) {
        this.title = title;
        this.time = time;
        this.repeat = false;
    }

    /*Конструктор неактивной задачи, которая выполняется с интервалом  */
    public Task(String title, int start, int end, int interval) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.repeat = true;
    }

    /*Геттер для названия */
    public String getTitle() {
        return title;
    }

    /*Сеттер для названия*/
    public void setTitle(String title) {
        this.title = title;
    }

    /*Метод для проверки состояния задачи */
    public boolean isActive() {
        return active;
    }

    /*Метод для установления состояния задачи */
    public void setActive(boolean active){
        this.active = active;
    }

    /*Геттер для времени. Возвращает время выполненения,
    или время начала для задачи которая повторяется  */
    public int getTime(){
        if (repeat) return start;
        else return time;
    }

    /*Сеттер для времени. Превращает повторяющуюся задачу в неповторяющуюся */
    public void setTime(int time){
        this.time = time;
        if (repeat) {
            start = 0;
            end = 0;
            interval = 0;
            repeat = false;
        }
    }

    /*Геттер для времени повторения. Если не повторяется возвращается время выполнения */
    public int getStartTime(){
        if (repeat) return start;
        else return time;
    }

    /*Геттер для конца выполнения повт. задачи. Если не повторяется возвращается время выполнения */
    public int getEndTime(){
        if (repeat) return end;
        else return time;
    }

    /*Геттер для интервала. Если не повторяется возвращается 0*/
    public int getRepeatInterval(){
        if (repeat) return interval;
        else return 0;
    }

    /*Сеттер для времени начала, конца и интервала. Если задача не повторяется, превращает в повт.*/
    public void setTime(int start, int end, int interval) {
        this.start = start;
        this.end = end;
        this.interval = interval;
        if (!repeat) {
            repeat = true;
            time = 0;
        }
    }

    /*Геттер для проверки состояния задачи. Повторяется или нет */
    public boolean isRepeated(){
        return repeat;
    }

    /*Ищет следующую задачу*/
    public int nextTimeAfter(int current) {
        if (!active) return -1;
        if (!repeat) {
            return (current >= time) ? -1 : time;
        }

        if (current < start ) return start;
        if (current >= end) return -1;

        ArrayList<Integer> nextTime = new ArrayList<Integer>();
        for(int j = start; j < end; j += interval) {
            nextTime.add(j);
        }

        for (int i = 0; i < nextTime.size(); i++) {
            if (current == nextTime.get(i)) {
                if (current == nextTime.get(nextTime.size() - 1)) {
                    return -1;
                }
                else return nextTime.get(i + 1);
            }
        }

        for (int i = 0; i < nextTime.size(); i++) {
            if (current >= nextTime.get(nextTime.size() - 1) && current < end) {
                return -1;
            }

            if (current > nextTime.get(i) && current < nextTime.get(i + 1)) {
                return nextTime.get(i+1);
            }
        }

        return -1;
    }

}