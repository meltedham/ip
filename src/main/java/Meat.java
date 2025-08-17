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
                String[] words = input.split(" "); //splits by spaces
                if (words[0].equals("mark"))
                {
                    int taskNum = Integer.parseInt(words[1]); //converts the number string to int
                    Task task = list.get(taskNum - 1);
                    task.Mark(); //mark the corresponding task as done
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(task.toString());
                } else if (words[0].equals("unmark"))
                {
                    int taskNum = Integer.parseInt(words[1]); //converts the number string to int
                    Task task = list.get(taskNum - 1);
                    task.Unmark(); //mark the corresponding task as done
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(task.toString());
                } else { //task name
                    list.add(new Task(input));
                    System.out.println("added: " + input);
                }
            }
            input = scanner.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }
}