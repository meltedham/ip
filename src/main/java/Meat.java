import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Meat {
    public static void main(String[] args) {
        FileEditor fileEditor = new FileEditor("meat.txt");
        //fileEditor.clearFile();
        String name = "Meat";
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?\n");

        ArrayList<Task> list = new ArrayList<>();
        fileEditor.fileToList(list);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine(); //entire line of input
        while (!input.equals("bye")) {
            String[] words = input.split(" ", 2); //splits into 2 parts(1st word and the rest)
            HandleInput handleInput = new HandleInput(list, input);
            switch (words[0]) {
                case "list":
                    if (handleInput.listValid()) {
                        System.out.println("Tasks:");
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println((i + 1) + ". " + list.get(i).toString());
                        };
                    }
                    break;
                case "mark":
                    if (handleInput.MUDValid()) {
                        int taskNum = Integer.parseInt(words[1]);
                        Task task = list.get(taskNum - 1);
                        task.Mark(); //mark the corresponding task as done
                        System.out.println("Marked this task as done:");
                        System.out.println(task.toString());
                        fileEditor.modifyFile(list);
                    }
                    break;
                case "unmark":
                    if (handleInput.MUDValid()) {
                        int taskNum = Integer.parseInt(words[1]);
                        Task task = list.get(taskNum - 1);
                        task.Unmark(); //mark the corresponding task as not done
                        System.out.println("Marked this task as not done:");
                        System.out.println(task.toString());
                        fileEditor.modifyFile(list);
                    }
                    break;
                case "delete":
                    if (handleInput.MUDValid()) {
                        System.out.println("Got it. I've removed this task:");
                        int taskNum = Integer.parseInt(words[1]);
                        System.out.println(list.get(taskNum - 1).toString());
                        list.remove(taskNum - 1);
                        fileEditor.modifyFile(list);
                        System.out.printf("Now you have %d tasks in the list.\n", list.size());
                    }
                    break;
                case "todo":
                    if (handleInput.todoValid()) {
                        Todo todo = new Todo(words[1]);
                        list.add(todo);
                        System.out.println("Added this task:");
                        System.out.println(todo.toString());
                        fileEditor.appendFile(todo);
                        System.out.printf("Now you have %d tasks in the list.\n", list.size());
                    }
                    break;
                case "deadline":
                    if (handleInput.deadlineValid()) {
                        String[] parts = words[1].split("/"); //split after the /
                        String[] time = parts[1].split(": ");
                        if (handleInput.dateValid(time[1])) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                            LocalDateTime endDateTime = LocalDateTime.parse(time[1], formatter);
                            Deadline deadline = new Deadline(parts[0], endDateTime);
                            list.add(deadline);
                            System.out.println("Got it. I've added this task:");
                            System.out.println(deadline.toString());
                            fileEditor.appendFile(deadline);
                            System.out.printf("Now you have %d tasks in the list.\n", list.size());
                        }
                    }
                    break;
                case "event":
                    if (handleInput.eventValid()) {
                        String[] parts = words[1].split("/"); //split after the /
                        String[] start = parts[1].split(": ");
                        String[] end = parts[2].split(": ");
                        if (handleInput.dateValid(end[1]) && handleInput.dateValid(start[1].stripTrailing())) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                            LocalDateTime endDateTime = LocalDateTime.parse(end[1], formatter);
                            LocalDateTime startDateTime = LocalDateTime.parse(start[1].stripTrailing(), formatter);
                            Event event = new Event(parts[0], endDateTime, startDateTime);
                            list.add(event);
                            System.out.println("Got it. I've added this task:");
                            System.out.println(event.toString());
                            fileEditor.appendFile(event);
                            System.out.printf("Now you have %d tasks in the list.\n", list.size());
                        }
                    }
                    break;
                default:
                    System.out.println(":( Not a valid command :(. Uses:");
                    System.out.println("list");
                    System.out.println("mark/unmark <task number>");
                    System.out.println("todo <description>");
                    System.out.println("deadline <description> /by DD.MM.YYYY hh:mm");
                    System.out.println("event <description> /from DD.MM.YYYY hh:mm /to DD.MM.YYYY hh:mm");
                    break;
            }
            input = scanner.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }
}