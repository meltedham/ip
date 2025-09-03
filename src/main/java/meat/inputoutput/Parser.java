package meat.inputoutput;

import meat.filestorage.Storage;

import meat.tasks.Deadline;
import meat.tasks.Event;
import meat.tasks.Tasklist;
import meat.tasks.Todo;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Parses and validates user input commands for tasks.
 * Handles commands like adding, marking, unmarking, deleting, and listing tasks.
 * Interacts with Ui for output, Tasklist for task management, and Storage for saving to file.
 */
public class Parser {

    /** User interface object for displaying messages. */
    private Ui ui;

    /** The task list managed by this parser. */
    private Tasklist taskList;

    /** Storage object for reading/writing tasks to file. */
    private Storage storage;

    /**
     * Constructs a Parser with the specified Ui, Tasklist, and Storage.
     *
     * @param ui the Ui object for displaying messages
     * @param taskList the Tasklist object for managing tasks
     * @param storage the Storage object for file operations
     */
    public Parser(Ui ui, Tasklist taskList, Storage storage) {
        this.ui = ui;
        this.taskList = taskList;
        this.storage = storage;
    }

    /**
     * Checks if user input is valid, and executes it if it is,
     * updating the list and file and printing the output accordingly.
     * If not valid, uses Ui to print error messages.
     *
     * @param input the user input command string
     */
    public void checkAnyValid(String input) {
        String[] words = input.split(" ", 2); //splits into 2 parts(1st word and the rest)
        switch (words[0]) {
        case "list":
            if (input.equals("list")) {
                this.ui.list();
            }
            break;
        case "mark":
            if (this.isMarkUnmarkDeleteValid(input)) {
                int taskNum = Integer.parseInt(words[1]);

                this.taskList.Mark(taskNum);
                this.ui.Mark(taskNum);
                storage.modifyFile(this.taskList);
            }
            break;
        case "unmark":
            if (this.isMarkUnmarkDeleteValid(input)) {
                int taskNum = Integer.parseInt(words[1]);

                this.taskList.Unmark(taskNum);
                this.ui.Unmark(taskNum);
                storage.modifyFile(this.taskList);
            }
            break;
        case "delete":
            if (this.isMarkUnmarkDeleteValid(input)) {
                int taskNum = Integer.parseInt(words[1]);

                this.ui.Delete(taskNum);
                this.taskList.Delete(taskNum);
                this.ui.taskCount();
                storage.modifyFile(this.taskList);
            }
            break;
        case "todo":
            if (this.isTodoValid(input)) {
                Todo todo = new Todo(words[1].trim());

                this.taskList.Add(todo);
                this.ui.Add(todo);
                this.storage.appendFile(todo);
            }
            break;
        case "deadline":
            if (this.isDeadlineValid(input)) {
                String[] parts = words[1].split("/"); //split after the /
                String[] time = parts[1].split(": ");
                if (this.isDateValid(time[1])) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                    LocalDateTime endDateTime = LocalDateTime.parse(time[1].trim(), formatter);
                    Deadline deadline = new Deadline(parts[0].trim(), endDateTime);

                    this.taskList.Add(deadline);
                    this.ui.Add(deadline);
                    this.storage.appendFile(deadline);
                }
            }
            break;
        case "event":
            if (this.isEventValid(input)) {
                String[] parts = words[1].split("/"); //split after the /
                String[] start = parts[1].split(": ");
                String[] end = parts[2].split(": ");
                if (this.isDateValid(end[1]) && this.isDateValid(start[1].stripTrailing())) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                    LocalDateTime endDateTime = LocalDateTime.parse(end[1].trim(), formatter);
                    LocalDateTime startDateTime = LocalDateTime.parse(start[1].trim(), formatter);
                    Event event = new Event(parts[0].trim(), endDateTime, startDateTime);

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

    /**
     * Checks if the input is a valid mark, unmark, or delete command.
     *
     * @param input the command string
     * @return true if valid, false otherwise
     */
    public boolean isMarkUnmarkDeleteValid(String input) {
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

    /**
     * Checks if the input is a valid todo command.
     *
     * @param input the command string
     * @return true if valid, else false
     */
    public boolean isTodoValid(String input) {
        String[] words = input.split(" ");
        if (words.length == 1) {
            this.ui.noTaskDesc();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if the input is a valid deadline command.
     *
     * @param input the command string
     * @return true if valid, else false
     */
    public boolean isDeadlineValid(String input) {
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

    /**
     * Checks if the input is a valid event command.
     *
     * @param input the command string
     * @return true if valid, else false
     */
    public boolean isEventValid (String input) {
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

    /**
     * Validates if a date string is in the correct format "dd.MM.yyyy HH:mm".
     *
     * @param date the date string
     * @return true if valid, else false
     */
    public boolean isDateValid(String date) {
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

