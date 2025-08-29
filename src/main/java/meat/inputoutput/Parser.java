package meat.inputoutput;

import meat.filestorage.Storage;
import meat.tasks.Deadline;
import meat.tasks.Event;
import meat.tasks.Tasklist;
import meat.tasks.Todo;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {
    //checks if input is valid and returns it if it is

    private Ui ui;
    private Tasklist taskList;
    private Storage storage;

    public Parser(Ui ui, Tasklist taskList, Storage storage) {
        this.ui = ui;
        this.taskList = taskList;
        this.storage = storage;
    }

    public void checkAnyValid(String input) {
        String[] words = input.split(" ", 2); //splits into 2 parts(1st word and the rest)
        switch (words[0]) {
            case "list":
                if (input.equals("list")) {
                    this.ui.list();
                }
                break;
            case "mark":
                if (this.MUDValid(input)) {
                    int taskNum = Integer.parseInt(words[1]);

                    this.taskList.Mark(taskNum);
                    this.ui.Mark(taskNum);
                    storage.modifyFile(this.taskList);
                }
                break;
            case "unmark":
                if (this.MUDValid(input)) {
                    int taskNum = Integer.parseInt(words[1]);

                    this.taskList.Unmark(taskNum);
                    this.ui.Unmark(taskNum);
                    storage.modifyFile(this.taskList);
                }
                break;
            case "delete":
                if (this.MUDValid(input)) {
                    int taskNum = Integer.parseInt(words[1]);

                    this.ui.Delete(taskNum);
                    this.taskList.Delete(taskNum);
                    this.ui.taskCount();
                    storage.modifyFile(this.taskList);
                }
                break;
            case "todo":
                if (this.todoValid(input)) {
                    Todo todo = new Todo(words[1]);

                    this.taskList.Add(todo);
                    this.ui.Add(todo);
                    this.storage.appendFile(todo);
                }
                break;
            case "deadline":
                if (this.deadlineValid(input)) {
                    String[] parts = words[1].split("/"); //split after the /
                    String[] time = parts[1].split(": ");
                    if (this.dateValid(time[1])) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                        LocalDateTime endDateTime = LocalDateTime.parse(time[1], formatter);
                        Deadline deadline = new Deadline(parts[0], endDateTime);

                        this.taskList.Add(deadline);
                        this.ui.Add(deadline);
                        this.storage.appendFile(deadline);
                    }
                }
                break;
            case "event":
                if (this.eventValid(input)) {
                    String[] parts = words[1].split("/"); //split after the /
                    String[] start = parts[1].split(": ");
                    String[] end = parts[2].split(": ");
                    if (this.dateValid(end[1]) && this.dateValid(start[1].stripTrailing())) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                        LocalDateTime endDateTime = LocalDateTime.parse(end[1], formatter);
                        LocalDateTime startDateTime = LocalDateTime.parse(start[1].stripTrailing(), formatter);
                        Event event = new Event(parts[0], endDateTime, startDateTime);

                        this.taskList.Add(event);
                        this.ui.Add(event);
                        this.storage.appendFile(event);
                    }
                }
                break;
            default:
                this.ui.Commands();
                break;
        }
    }

    public boolean MUDValid(String input) {
        String[] words = input.split(" ");
        if (words.length == 1) {
            this.ui.noTaskNum();
            return false;
        } else {
            try {
                int taskNum = Integer.parseInt(words[1]); //converts the number string to int
                if (taskList.taskCount() < taskNum || taskNum < 1) {
                    this.ui.invalidTaskNum();
                    return false;
                } else {
                    return true;
                }
            } catch (NumberFormatException e) {
                this.ui.noTaskNum();
                return false;
            }
        }
    }

    public boolean todoValid(String input) {
        String[] words = input.split(" ");
        if (words.length == 1) {
            this.ui.noTaskDesc();
            return false;
        } else {
            return true;
        }
    }

    public boolean deadlineValid(String input) {
        String[] split = input.split(" ", 2);
        if (split.length == 1) { //if no task description
            this.ui.noTaskDesc();
            return false;
        } else {
            String[] split2nd = split[1].split("/", 2); // "task" "/by: date/time"
            if (split2nd.length == 1) { //if the date/time is 1 word long
                this.ui.deadlineInvalid();
                return false;
            } else {
                String[] split3rd = split[1].split(" ", 2); // "/by:" "date/time"
                if (split3rd.length < 2) {
                    this.ui.dateInvalid();
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    public boolean eventValid (String input) {
        String[] words = input.split(" ", 2);
        if (words.length == 1) {
            this.ui.noTaskDesc();
            return false;
        } else {
            String[] parts = words[1].split("/"); //split after the /
            if (parts.length < 3) {
                this.ui.eventInvalid();
                return false;
            } else {
                String[] start = parts[1].split(" ", 2);
                String[] end = parts[2].split(" ", 2);
                if (start.length < 2 || end.length < 2) {
                    this.ui.eventInvalid();
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
            this.ui.dateInvalid();
            return false;
        }
    }
}

