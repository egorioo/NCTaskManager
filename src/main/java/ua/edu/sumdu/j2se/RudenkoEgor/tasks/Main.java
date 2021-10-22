package ua.edu.sumdu.j2se.RudenkoEgor.tasks;

public class Main {

	public static void main(String[] args) {
		System.out.println("Test 1");
		Task task = new Task("some", 10);
		task.setActive(true);
		System.out.println("Expected 10, result = " + task.nextTimeAfter(0));
		System.out.println("Expected 10, result = " + task.nextTimeAfter(9));
		System.out.println("Expected -1, result = " + task.nextTimeAfter(10));
		System.out.println("Expected -1, result = " + task.nextTimeAfter(100));

		System.out.println("Test 2");
		Task task2 = new Task("some", 10, 100, 20);
		task2.setActive(true);
		System.out.println("Expected 10, result = " + task2.nextTimeAfter(0));
		System.out.println("Expected 10, result = " + task2.nextTimeAfter(9));
		System.out.println("Expected 50, result = " + task2.nextTimeAfter(30));
		System.out.println("Expected 50, result = " + task2.nextTimeAfter(40));
		System.out.println("Expected 30, result = " + task2.nextTimeAfter(10));
		System.out.println("Expected -1, result = " + task2.nextTimeAfter(95));
		System.out.println("Expected -1, result = " + task2.nextTimeAfter(100));

		System.out.println("Test 3");
		Task task3 = new Task("some", 10);
		task3.setActive(false);
		System.out.println("Expected -1, result = " + task3.nextTimeAfter(0));
	}
}