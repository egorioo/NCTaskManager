package ua.edu.sumdu.j2se.RudenkoEgor.tasks;

public class Main {

	public static void main(String[] args) {

		Task A = new Task("A", 42);
		Task B = new Task("B", 42);
		Task C = new Task("C", 42);
		Task D = new Task("D", 42);
		Task E = new Task("E", 42);

		ArrayTaskList arr2 = new ArrayTaskList();


		System.out.println("TEST1");
		System.out.println("ArrayTaskList.size(). Expected:0. My result: " + arr2.size());
		arr2.add(A);
		arr2.add(B);
		arr2.add(C);
		System.out.println("ArrayTaskList.size(). Expected:3. My result: " + arr2.size());

		System.out.println("TEST2");

		ArrayTaskList arr = new ArrayTaskList();
		arr.add(A);
		arr.add(B);
		arr.add(C);
		arr.add(D);
		arr.add(E);

		System.out.println("ArrayTaskList.remove(A) повинно бути істинним. My result: " + arr.remove(A));
		System.out.println("ArrayTaskList.size() Expected: 4. My result: " + arr.size());

		System.out.println("-----------");

		System.out.println("ArrayTaskList.remove(E) повинно бути істинним. My result: " + arr.remove(E));
		System.out.println("ArrayTaskList.size(). Expected: 3. My result: " + arr.size());

		System.out.println("-----------");

		System.out.println("ArrayTaskList.remove(C) повинно бути істинним. My result: " + arr.remove(C));
		System.out.println("ArrayTaskList.size(). Expected: 2. My result: " + arr.size());

		System.out.println("-----------");

		System.out.println("ArrayTaskList.remove(D) не повинно бути істинним. My result: " + arr.remove(new Task("F", 42)));
		System.out.println("repeated ArrayTaskList.remove(D) не повинно бути істинним. My result: " + arr.remove(new Task("F1", 5,10,2)));

		System.out.println("-----------");

		System.out.println("Add 2 elem A");
		arr.add(A);
		arr.add(A);
		System.out.println("ArrayTaskList.size(). Expected: 4. My result: " + arr.size());
		System.out.println("ArrayTaskList.remove(A) повинно бути істинним. My result: " + arr.remove(A));
		System.out.println("ArrayTaskList.size(). Expected: 3. My result: " + arr.size());
		System.out.println("ArrayTaskList.remove(A) повинно бути істинним. My result: " + arr.remove(A));
		System.out.println("ArrayTaskList.size(). Expected: 2. My result: " + arr.size());

		System.out.println("-------------------");
		ArrayTaskList arr3 = new ArrayTaskList();
		Task ex1 = new Task("1", 9);
		Task ex2 = new Task("2", 15,25,5);
		Task ex3 = new Task("3", 26);
		Task ex4 = new Task("4", 10,14,2);

		arr3.add(ex1);
		arr3.add(ex2);
		arr3.add(ex3);
		arr3.add(ex4);

		//arr3.incoming(0,30);

		System.out.println(ex1.nextTimeAfter(5));
		System.out.println(ex2.nextTimeAfter(20));

		Task task = new Task("some", 10, 100, 20);
		System.out.println(task.nextTimeAfter(40));
	}
}