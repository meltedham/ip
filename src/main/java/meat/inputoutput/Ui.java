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
     */
    public void Start(String name) {
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?\n");
    }

    /** Displays all tasks in the Tasklist. */
    public void list() {
        System.out.println("Tasks:");
        this.taskList.printList();
    }

    /** Asks the user to provide a task number. */
    public void noTaskNum() {
        System.out.println("Provide a task number >:(");
    }

    /** Informs the user that the task number is invalid. */
    public void invalidTaskNum() {
        System.out.println("Provide a valid task number >:(");
    }

    /**
     * Prints the removed task information.
     *
     * @param taskNum the number of the task to display
     */
    public void Delete(int taskNum) {
        System.out.println("Got it. I've removed this task:");
        this.taskList.printTask(taskNum);
    }

    /** Displays the current number of tasks in the Tasklist. */
    public void taskCount() {
        System.out.printf("Now you have %d tasks in the list.\n", this.taskList.taskCount());
    }

    /**
     * Prints a task marked as done.
     *
     * @param taskNum the number of the task to mark
     */
    public void Mark(int taskNum) {
        System.out.println("Marked this task as done:");
        this.taskList.printTask(taskNum);
    }

    /**
     * Prints a task marked as not done.
     *
     * @param taskNum the number of the task to unmark
     */
    public void Unmark(int taskNum) {
        System.out.println("Marked this task as not done:");
        this.taskList.printTask(taskNum);
    }

    /**
     * Displays a newly added task and updates the task count.
     *
     * @param task the task that was added
     */
    public void Add(Task task) {
        System.out.println("Added this task:");
        System.out.println(task.toString());
        this.taskCount();
    }

    /** Informs the user that a task description is missing. */
    public void noTaskDesc() {
        System.out.println("Provide a task description >:(");
    }

    /** Informs the user of invalid deadline syntax. */
    public void deadlineInvalid() {
        System.out.println(":( Invalid syntax for the end date and time of the deadline :(");
        System.out.println("Enter: deadline <description> /by DD.MM.YYYY hh:mm");
    }

    /** Informs the user of invalid event syntax. */
    public void eventInvalid() {
        System.out.println(":( Invalid syntaxf for the start and end date and time of the event :(");
        System.out.println("Enter: event <description> /from DD.MM.YYYY hh:mm /to DD.MM.YYYY hh:mm");
    }

    /** Informs the user of invalid date/time format. */
    public void dateInvalid() {
        System.out.println(":( Invalid syntax for date/time :(");
        System.out.println("Enter: day.month.year hours: minutes - DD.MM.YYYY hh:mm");
    }

    /** Displays the list of all valid commands. */
    public void Commands() {
        System.out.println(":( Not a valid command :(. Commands:");
        System.out.println("list");
        System.out.println("mark/unmark/delete <task number>");
        System.out.println("todo <description>");
        System.out.println("deadline <description> /by DD.MM.YYYY hh:mm");
        System.out.println("event <description> /from DD.MM.YYYY hh:mm /to DD.MM.YYYY hh:mm");
    }
}

