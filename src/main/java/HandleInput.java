import java.time.DateTimeException;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HandleInput {
    private ArrayList<Task> list;
    private String input;

    public HandleInput(ArrayList<Task> list, String input) {
        this.list = list;
        this.input = input;
    }

    public boolean listValid() {
        return input.equals("list");
    }

    public boolean MUDValid() {
        String[] words = input.split(" ");
        if (words.length == 1) {
            System.out.println("Provide a task number >:(");
            return false;
        } else {
            try {
                int taskNum = Integer.parseInt(words[1]); //converts the number string to int
                if (list.size() < taskNum || taskNum < 1) {
                    System.out.println("Provide a valid task number >:(");
                    return false;
                } else {
                    return true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Provide a task number >:(");
                return false;
            }
        }
    }

    public boolean todoValid() {
        String[] words = input.split(" ");
        if (words.length == 1) {
            System.out.println("Provide a task description >:(");
            return false;
        } else {
            return true;
        }
    }

    public boolean deadlineValid() {
        String[] split = input.split(" ", 2);
        if (split.length == 1) { //if no task description
            System.out.println("Provide a task description >:(");
            return false;
        } else {
            String[] split2nd = split[1].split("/", 2); // "task" "/by: date/time"
            if (split2nd.length == 1) { //if the date/time is 1 word long
                System.out.println(":( There must be a end date and time for a deadline :(");
                System.out.println("Enter: deadline <description> /by DD.MM.YYYY hh:mm");
                return false;
            } else {
                String[] split3rd = split[1].split(" ", 2); // "/by:" "date/time"
                if (split3rd.length < 2) {
                    System.out.println(":( Invalid syntax for time/date :(");
                    System.out.println("Enter: deadline <description> /by DD.MM.YYYY hh:mm");
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    public boolean eventValid () {
        String[] words = input.split(" ", 2);
        if (words.length == 1) {
            System.out.println("Provide a task description >:(");
            return false;
        } else {
            String[] parts = words[1].split("/"); //split after the /
            if (parts.length < 3) {
                System.out.println(":( There must be a start and end date and time for an event :(");
                System.out.println("Enter: event <description> /from DD.MM.YYYY hh:mm /to DD.MM.YYYY hh:mm");
                return false;
            } else {
                String[] start = parts[1].split(" ", 2);
                String[] end = parts[2].split(" ", 2);
                if (start.length < 2 || end.length < 2) {
                    System.out.println(":( Invalid syntax :(");
                    System.out.println("Enter: event <description> /from DD.MM.YYYY hh:mm /to DD.MM.YYYY hh:mm");
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    public boolean dateValid(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            return true;
        } catch (DateTimeException e) {
            System.out.println(date + "Enter in date.month.year hours:minutes - DDM.MM.YYYY HH:mm format :(");
            return false;
        }
    }
}

