package meat.inputoutput;

import meat.tasks.Task;
import meat.tasks.Tasklist;

/**
 * Handles all user interface interactions.
 * Prints prompts, results, and tasks to the console.
 * Works with Tasklist to print tasks from the list.
 */
public class Ui {

    /** The task list used to retrieve and print tasks. */
    private Tasklist taskList;

    /**
     * Constructs a Ui with the given Tasklist.
     *
     * @param taskList the Tasklist to interact with
     */
    public Ui(Tasklist taskList) {
        this.taskList = taskList;
    }

    /**
     * Displays a start greeting with the program name.
     *
     * @param name the program's name
     * @return a String of the start greeting with the program name
     */
    public String start(String name) {
        return "Hello! I'm " + name + "\n" + "What can I do for you?\n";
    }

    /** Displays all tasks in the Tasklist.
     *
     * @return a String of the tasks in the tasklist
     */
    public String list() {
        return "Tasks:\n" + this.taskList.printList();
    }

    /** Informs the user that the task number is invalid.
     *
     * @return A String representing an invalid task number error message
     */
    public String invalidTaskNum() {
        return "Provide a valid task number >:(";
    }

    /**
     * Prints the removed task information.
     *
     * @param taskNum the number of the task to display
     * @return A String representing a task removal message
     */
    public String delete(int taskNum) {
        return "Got it. I've removed this task:\n" + this.taskList.printTask(taskNum);
    }

    /** Displays the current number of tasks in the Tasklist.
     *
     * @return A String representing the tasks in the list
     */
    public String taskCount() {
        return "Now you have " + this.taskList.taskCount() + " tasks in the list.\n";
    }

    /**
     * Prints a task marked as done.
     *
     * @param taskNum the number of the task to mark
     * @return A String representing a task marked message
     */
    public String mark(int taskNum) {
        return "Marked this task as done:\n"+ this.taskList.printTask(taskNum);
    }

    /**
     * Prints a task marked as not done.
     *
     * @param taskNum the number of the task to unmark
     * @return A String representing a task unmarked message
     */
    public String unmark(int taskNum) {
        return "Marked this task as not done:\n" + this.taskList.printTask(taskNum);
    }

    /**
     * Displays a newly added task and updates the task count.
     *
     * @param task the task that was added
     * @return A String representing an add task message
     */
    public String add(Task task) {
        return "Added this task:\n" + task.toString() + "\n" + this.taskCount();
    }

    /** Informs the user that a task description is missing.
     *
     * @return A String representing a missing task description message
     */
    public String noTaskDesc() {
        return "Provide a task description >:(";
    }

    /** Informs the user of invalid deadline syntax.
     *
     * @return A String representing an invalid deadline error message
     */
    public String invalidDeadline() {
        return ":( Invalid syntax for the end date and time of the deadline :(\n"
            + "Enter: deadline <description> /by DD.MM.YYYY hh:mm";
    }

    /** Informs the user of invalid event syntax.
     *
     * @return A String representing an invalid event error message
     */
    public String invalidEvent() {
        return ":( Invalid syntax for the start and end date and time of the event :(\n"
            + "Enter: event <description> /from DD.MM.YYYY hh:mm /to DD.MM.YYYY hh:mm";
    }

    /** Informs the user of invalid date/time format.
     *
     * @return A String representing an invalid date error message
     */
    public String invalidDate() {
        return ":( Invalid syntax for date/time :(\n"
                + "Enter: day.month.year hours: minutes - DD.MM.YYYY hh:mm";
    }

    /** Displays the list of all valid commands.
     *
     * @return A String representing all the valid commands
     */
    public String commands() {
        return ":( Not a valid command :(. Commands:\n"
                + "list\n" + "mark/unmark/delete <task number>\n"
                + "todo <description>\n" + "deadline <description> /by DD.MM.YYYY hh:mm\n"
                + "event <description> /from DD.MM.YYYY hh:mm /to DD.MM.YYYY hh:mm\n";
    }

    /**
     * Displays a header message before the list of tasks containing keyword is printed.
     *
     * @param keyword the keyword to search the tasks by
     * @return A String representing a find command message
     */
    public String find(String keyword) {
        return "Here are the matching tasks in your list:\n" + this.taskList.printByKeyword(keyword);
    }

    /**
     * Informs the user of invalid find syntax.
     *
     * @return A String representing an invalid find command
     */
    public String invalidFind() {
        return "Provide a keyword to search by >:(";
    }

    /**
     * Displays a header message before the list of tasks on a particular date is printed.
     *
     * @param date the date to search the tasks by
     * @return A String representing a schedule command message
     */
    public String schedule(String date) {
        return "Here's your schedule for " + date + ":\n" + this.taskList.printByDate(date);
    }

    /**
     * Informs the user of invalid schedule syntax.
     *
     * @return A String representing an invalid schedule command
     */
    public String invalidSchedule() {
        return "Date provided to view schedule is in the wrong format :(\n" + "Enter: schedule DD.MM.YYYY";
    }
}

