import java.util.ArrayList;
import java.util.Scanner;

public class Meat {
    public static void main(String[] args) {
        FileEditor fileEditor = new FileEditor("meat.txt");
        //fileEditor.clearFile();
        String name = "Meat";
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?\n");

        ArrayList<Task> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine(); //entire line of input
        while (!input.equals("bye"))
        {
            String str = "";
            if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                fileEditor.printFile();
            } else { //input = mark/unmark/task name/other/delete
                String[] words = input.split(" ", 2); //splits into 2 parts(1st word and the rest)
                if (words.length == 1) {
                    if (words[0].equals("mark") || words[0].equals("unmark") || words[0].equals("delete")) {
                        System.out.printf(":( The task number to %s cannot be empty :(\n", input);
                    } else if (words[0].equals("todo") || words[0].equals("deadline") || words[0].equals("event")) {
                        System.out.printf(":( There must be a description for a %s :(\n", input);
                    } else {
                        System.out.println(":( Not a valid command :(. Uses:");
                        System.out.println("list");
                        System.out.println("mark/unmark <task number>");
                        System.out.println("todo <description>");
                        System.out.println("deadline <description> /by <end date/time>");
                        System.out.println("event <description> /from <start date/time> /to <end date/time>");
                    }
                } else {
                    if (words[0].equals("mark")) {
                        int taskNum = Integer.parseInt(words[1]); //converts the number string to int
                        if (list.size() < taskNum || taskNum < 1) {
                            System.out.println(":( Invalid task number :(.");
                        } else {
                            Task task = list.get(taskNum - 1);
                            task.Mark(); //mark the corresponding task as done
                            System.out.println("Nice! I've marked this task as done:");
                            System.out.println(task.toString());
                            fileEditor.modifyFile(list);
                        }
                    } else if (words[0].equals("unmark")) {
                        int taskNum = Integer.parseInt(words[1]); //converts the number string to int
                        if (list.size() < taskNum || taskNum < 1) {
                            System.out.println(":( Invalid task number :(.");
                        } else {
                            Task task = list.get(taskNum - 1);
                            task.Unmark(); //mark the corresponding task as done
                            System.out.println("OK, I've marked this task as not done yet:");
                            System.out.println(task.toString());
                            fileEditor.modifyFile(list);
                        }
                    } else if (words[0].equals("todo")) { //task name
                        Todo todo = new Todo(words[1]);
                        list.add(todo);
                        System.out.println("Got it. I've added this task:");
                        System.out.println(todo.toString());
                        fileEditor.appendFile(todo.toString());
                        System.out.printf("Now you have %d tasks in the list.\n", list.size());

                    } else if (words[0].equals("deadline")) {
                        String[] parts = words[1].split("/"); //split after the /
                        if (parts.length == 1) {
                            System.out.println(":( There must be a end date/time for a deadline :(. Enter: deadline <description> /by <end date/time>");
                        } else {
                            String[] time = parts[1].split(" ", 2);
                            if (time.length < 2) {
                                System.out.println(":( Invalid syntax :(. Enter: deadline <description> /by <end date/time>");
                            } else {
                                Deadline deadline = new Deadline(parts[0], time[1]);
                                list.add(deadline);
                                System.out.println("Got it. I've added this task:");
                                System.out.println(deadline.toString());
                                fileEditor.appendFile(deadline.toString());
                                System.out.printf("Now you have %d tasks in the list.\n", list.size());
                            }
                        }
                    } else if (words[0].equals("event")) {
                        String[] parts = words[1].split("/"); //split after the /
                        if (parts.length < 3) {
                            System.out.println(":( There must be a start and end date/time for an event :(. Enter: event <description> /from <start date/time> /to <end date/time>");
                        } else {
                            String[] start = parts[1].split(" ", 2);
                            String[] end = parts[2].split(" ", 2);
                            if (start.length < 2 || end.length < 2) {
                                System.out.println(":( Invalid syntax :(. Enter: event <description> /from <start date/time> /to <end date/time>");
                            } else {
                                Event event = new Event(parts[0], end[1], start[1]);
                                list.add(event);
                                System.out.println("Got it. I've added this task:");
                                System.out.println(event.toString());
                                fileEditor.appendFile(event.toString());
                                System.out.printf("Now you have %d tasks in the list.\n", list.size());
                            }
                        }
                    } else if (words[0].equals("delete")) {
                        int taskNum = Integer.parseInt(words[1]);
                        if (list.size() < taskNum || taskNum < 1) {
                            System.out.println(":( Invalid task number :(.");
                        } else {
                            System.out.println("Got it. I've removed this task:");
                            System.out.println(list.get(taskNum - 1).toString());
                            list.remove(taskNum - 1);
                            fileEditor.modifyFile(list);
                            System.out.printf("Now you have %d tasks in the list.\n", list.size());
                        }
                    } else { //more than 1 word but invalid
                        System.out.println(":( Not a valid command :(. Uses:");
                        System.out.println("list");
                        System.out.println("mark/unmark <task number>");
                        System.out.println("todo <description>");
                        System.out.println("deadline <description> /by <end date/time>");
                        System.out.println("event <description> /from <start date/time> /to <end date/time>");
                    }
                }
            }
            input = scanner.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }
}