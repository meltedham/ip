import java.util.ArrayList;
import java.util.Scanner;

public class Meat {
    public static void main(String[] args) {
        String name = "Meat";
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?\n");

        ArrayList<Task> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine(); //entire line of input
        while (!input.equals("bye"))
        {
            if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(i + 1 + ". " + list.get(i).toString());
                }
            } else { //input = mark/unmark/task name
                String[] words = input.split(" ", 2); //splits into 2 parts(1st word and the rest)
                if (words[0].equals("mark")) {
                    int taskNum = Integer.parseInt(words[1]); //converts the number string to int
                    Task task = list.get(taskNum - 1);
                    task.Mark(); //mark the corresponding task as done
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(task.toString());
                } else if (words[0].equals("unmark")) {
                    int taskNum = Integer.parseInt(words[1]); //converts the number string to int
                    Task task = list.get(taskNum - 1);
                    task.Unmark(); //mark the corresponding task as done
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(task.toString());
                } else if (words[0].equals("todo")) { //task name
                    Todo todo = new Todo(words[1]);
                    list.add(todo);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(todo.toString());
                    System.out.printf("Now you have %d tasks in the list.\n", list.size());
                } else if (words[0].equals("deadline")) {
                    String[] parts = words[1].split("/"); //split after the /
                    String[] time = parts[1].split(" ", 2);
                    Deadline deadline = new Deadline(parts[0], time[1]);
                    list.add(deadline);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(deadline.toString());
                    System.out.printf("Now you have %d tasks in the list.\n", list.size());
                } else if (words[0].equals("event")) {
                    String[] parts = words[1].split("/"); //split after the /
                    String[] start = parts[1].split(" ", 2);
                    String[] end = parts[2].split(" ", 2);
                    Event event = new Event(parts[0], end[1], start[1]);
                    list.add(event);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(event.toString());
                    System.out.printf("Now you have %d tasks in the list.\n", list.size());
                }
            }
            input = scanner.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }
}