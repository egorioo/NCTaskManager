package ua.edu.sumdu.j2se.rudenko.tasks;

public class Main {

    public static void main(String[] args) {
        /*AbstractTaskList list = new ArrayTaskList();
        AbstractTaskList list2 = new ArrayTaskList();
        Task A = new Task("A",10);
        IntStream.range(0, 10).forEach(i -> list.add(A));
        IntStream.range(0, 10).forEach(i -> list2.add(A));
        IntStream.range(0, list.size()).mapToObj(i -> list.getTask(0)).forEach(list::remove);
        IntStream.range(0, 10).forEach(i -> list.add(A));
        IntStream.range(0, list2.size()).mapToObj(i -> list2.getTask(0)).forEach(list2::remove);
        IntStream.range(0, 10).forEach(i -> list2.add(A));
        System.out.println(list.hashCode());
        System.out.println(list2.hashCode());
        System.out.println(list.hashCode() == list2.hashCode());
        Iterator<Task> iter3 = list.iterator();
        while (iter3.hasNext()) {
            System.out.println(iter3.next().getTitle());
        }*/

        Task task1 = new Task("A", 5);
        Task task2 = new Task("B", 5);
        Task task3 = new Task("C", 5);
        Task task4 = new Task("D", 5);

        LinkedTaskList list = new LinkedTaskList();
        list.add(task1);
        list.add(task2);
        list.add(task3);
        System.out.println(list.hashCode());

        LinkedTaskList list2 = new LinkedTaskList();
        list2.add(task1);
        list2.add(task2);
        list2.add(task3);
        System.out.println(list2.hashCode());

        System.out.println(list.equals(list2));
    }
}