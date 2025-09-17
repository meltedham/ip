package meat.inputoutput;

import meat.filestorage.Storage;

import meat.tasks.Deadline;
import meat.tasks.Event;
import meat.tasks.Tasklist;
import meat.tasks.Todo;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Parses and validates user input commands for tasks.
 * Handles adding, marking, unmarking, deleting, finding and listing tasks.
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
        assert ui != null : "Ui for Parser cannot be null";
        assert taskList != null : "Tasklist for Parser cannot be null";
        assert storage != null : "Storage for Parser cannot be null";
        this.ui = ui;
        this.taskList = taskList;
        this.storage = storage;
    }

    /**
     * Checks if user input is valid, and executes it if it is,
     * updating the list and file and returning an output accordingly.
     * If not valid, uses Ui to return error messages.
     *
     * @param input the user input command string
     * @return A String representing Meat's response to the input
     */
    public String checkAnyValid(String input) {
        assert input != null : "Cannot parse a null input";
        String[] words = input.split(" ", 2); //splits into 2 parts(1st word and the rest)
        switch (words[0]) {
            case "list":
                if (input.equals("list")) {
                    return this.ui.list();
                }
                break;
            case "mark":
                return handleMark(input);
            case "unmark":
                return handleUnmark(input);
            case "delete":
                return handleDelete(input);
            case "todo":
                return handleTodo(input);
            case "deadline":
                return handleDeadline(input);
            case "event":
                return handleEvent(input);
            case "find":
                return handleFind(input);
            case "schedule":
                return handleSchedule(input);
            default:
                return this.ui.commands();
        }
        return "";
    }

    /**
     * Checks if the mark command is valid, and executes it if valid
     * Returns messages to show the success or failure.
     *
     * @param input the mark command input
     * @return a confirmation message from the UI
     */
    public String handleMark(String input) {
        String[] words = input.split(" ", 2); //splits into 2 parts(1st word and the rest)
        if (this.hasValidTaskNum(input)) {
            int taskNum = Integer.parseInt(words[1]);
            this.taskList.mark(taskNum);
            storage.modifyFile(this.taskList);
            return this.ui.mark(taskNum);
        } else {
            return this.ui.invalidTaskNum();
        }
    }

    /**
     * Checks if the unmark command is valid, and executes it if valid
     * Returns messages to show the success or failure.
     *
     * @param input the mark command input
     * @return a confirmation message from the UI
     */
    public String handleUnmark(String input){
        String[] words = input.split(" ", 2); //splits into 2 parts(1st word and the rest)
        if (this.hasValidTaskNum(input)) {
            int taskNum = Integer.parseInt(words[1]);
            this.taskList.unmark(taskNum);
            storage.modifyFile(this.taskList);
            return this.ui.unmark(taskNum);
        } else {
            return this.ui.invalidTaskNum();
        }
    }

    /**
     * Checks if the delete command is valid, and executes it if valid
     *
     * @param input the mark command input
     * @return a deletion confirmation message and the new task count
     */
    public String handleDelete(String input){
        String[] words = input.split(" ", 2); //splits into 2 parts(1st word and the rest)
        if (this.hasValidTaskNum(input)) {
            int taskNum = Integer.parseInt(words[1]);

            String message = this.ui.delete(taskNum);
            this.taskList.delete(taskNum);
            message = message + "\n" + this.ui.taskCount();
            storage.modifyFile(this.taskList);
            return message;
        } else {
            return this.ui.invalidTaskNum();
        }
    }

    /**
     * Checks if a todo command is valid, and creates and adds a Todo task
     * to the task list if valid, else returns an error message
     *
     * @param input the task description
     * @return a confirmation message from the UI
     */
    public String handleTodo(String input) {
        String[] words = input.split(" ", 2); //splits into 2 parts(1st word and the rest)
        if (this.hasName(input)) {
            Todo todo = new Todo(words[1].trim());
            if (this.taskList.hasDuplicate(todo)) {
                return this.ui.duplicateTasks();
            } else {
                this.taskList.add(todo);
                this.storage.appendFile(todo);
                return this.ui.add(todo);
            }
        } else {
            return this.ui.noTaskDesc();
        }
    }

    /**
     * Checks if a deadline command is valid, and creates and adds a Deadline task
     * to the task list, if end time has the format: "dd.MM.yyyy HH:mm", else returns
     * an error message.
     *
     * @param input the description and deadline string
     * @return a confirmation message if valid, else an invalid date message
     */
    public String handleDeadline(String input) {
        String[] words = input.split(" ", 2); //splits into 2 parts(1st word and the rest)
        if (this.hasName(input)) {
            if (this.isDeadlineValid(input)) {
                String[] parts = words[1].split("/"); //split after the /
                String[] time = parts[1].split(": ");
                if (this.isDateValid(time[1])) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                    LocalDateTime endDateTime = LocalDateTime.parse(time[1].trim(), formatter);
                    Deadline deadline = new Deadline(parts[0].trim(), endDateTime);

                    if (this.taskList.hasDuplicate(deadline)) {
                        return this.ui.duplicateTasks();
                    } else {
                        this.taskList.add(deadline);
                        this.storage.appendFile(deadline);
                        return this.ui.add(deadline);
                    }
                } else {
                    return this.ui.invalidDate();
                }
            } else {
                return this.ui.invalidDeadline();
            }
        } else {
            return this.ui.noTaskDesc();
        }
    }

    /**
     * Checks if an event command is valid, and creates and adds an Event task
     * to the task list, if start and end time has the format: "dd.MM.yyyy HH:mm",
     * else returns an error message
     *
     * @param input the description and event time range
     * @return a confirmation message if valid, else an invalid date message
     */
    public String handleEvent(String input){
        String[] words = input.split(" ", 2); //splits into 2 parts(1st word and the rest)
        if (this.hasName(input)) {
            if (this.isEventValid(input)) {
                String[] parts = words[1].split("/"); //split after the /
                String[] start = parts[1].split(": ");
                String[] end = parts[2].split(": ");
                if (this.isDateValid(end[1]) && this.isDateValid(start[1].stripTrailing())) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                    LocalDateTime endDateTime = LocalDateTime.parse(end[1].trim(), formatter);
                    LocalDateTime startDateTime = LocalDateTime.parse(start[1].trim(), formatter);
                    if (endDateTime.isAfter(startDateTime)) { //Ai-Assisted: ChatGPT, for error handling
                        Event event = new Event(parts[0].trim(), endDateTime, startDateTime);
                        if (this.taskList.hasDuplicate(event)) {
                            return this.ui.duplicateTasks();
                        } else {
                            this.taskList.add(event);
                            this.storage.appendFile(event);
                            return this.ui.add(event);
                        }
                    } else {
                        return this.ui.endBeforeStart();
                    }
                } else {
                    return this.ui.invalidDate();
                }
            } else {
                return this.ui.invalidEvent();
            }
        } else {
            return this.ui.noTaskDesc();
        }
    }

    /**
     * Checks if the find command is valid, and executes it if valid
     * Returns messages to show the success or failure.
     *
     * @param input the find command input
     * @return a list of the tasks that match the search keyword if the command is valid,
     * else an error message
     */
    public String handleFind(String input) {
        String[] words = input.split(" ", 2); //splits into 2 parts(1st word and the rest)
        if (this.isFindValid(input)) {
            String keyword = words[1].trim();
            return this.ui.find(keyword);
        } else {
            return this.ui.invalidFind();
        }
    }

    /**
     * Checks if the schedule command is valid, and executes it if valid
     * Returns messages to show the success or failure.
     *
     * @param input the schedule command input
     * @return a list of the tasks that match the search date if the command is valid,
     * else an error message
     */
    public String handleSchedule(String input) {
        String[] words = input.split(" ", 2); //splits into 2 parts(1st word and the rest)
        if (this.isScheduleValid(input)) {
            return this.ui.schedule(words[1].trim());
        } else {
            return this.ui.invalidSchedule();
        }
    }

    /**
     * Checks if the input has a valid task number.
     *
     * @param input the command string
     * @return true if valid, false otherwise
     */
    public boolean hasValidTaskNum(String input) {
        assert input != null : "Input to check cannot be null";
        String[] words = input.split(" ");
        if (words.length == 1) {
            return false;
        } else {
            try {
                int taskNum = Integer.parseInt(words[1]); //converts the number string to int
                if (taskList.taskCount() < taskNum || taskNum < 1) {
                    return false;
                } else {
                    return true;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    /**
     * Checks if the input has a task name
     *
     * @param input the command string
     * @return true if valid, else false
     */
    public boolean hasName(String input) {
        assert input != null : "Input to check cannot be null";
        String[] words = input.split(" ");
        if (words.length == 1) {
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
        assert input != null : "Input to check cannot be null";
        String[] split = input.split(" ", 2);
        String[] split2nd = split[1].split("/", 2); // "task" "/by: date/time"
        if (split2nd.length == 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if the input is a valid event command.
     *
     * @param input the command string
     * @return true if valid, else false
     */
    public boolean isEventValid (String input) {
        assert input != null : "Input to check cannot be null";
        String[] words = input.split(" ", 2);
        String[] parts = words[1].split("/"); //split after the /
        if (parts.length < 3) {
            return false;
        } else {
            String[] start = parts[1].split(" ", 2);
            String[] end = parts[2].split(" ", 2);
            if (start.length < 2 || end.length < 2) {
                return false;
            } else {
                return true;
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
        assert date != null : "Input to check cannot be null";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * Checks if the input is a valid find command.
     *
     * @param input the command string
     * @return true if valid, else false
     */
    public boolean isFindValid(String input) {
        assert input != null : "Input to check cannot be null";
        String[] words = input.split(" ", 2);
        if (words.length == 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if the input is a valid schedule command, by checking if a date is provided,
     * and if it is in the format "dd.MM.yyyy".
     *
     * @param input the command string
     * @return true if valid, else false
     */
    public boolean isScheduleValid(String input) {
        assert input != null : "Date to view schedule by cannot be null";
        String[] words = input.split(" ", 2);
        String date = words[1].trim();
        if (words.length == 1) {
            return false;
        } else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate dateFormatted = LocalDate.parse(date, formatter);
                return true;
            } catch (DateTimeException e) {
                return false;
            }
        }
    }

}

