import java.time.DateTimeException;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ui {
    //prints the prompts and results

    private Tasklist taskList;

    public Ui(Tasklist taskList) {
        this.taskList = taskList;
    }

    public void Start(String name) {
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?\n");
    }

    public void list() {
        System.out.println("Tasks:");
        this.taskList.printList();
    }

    public void noTaskNum() {
        System.out.println("Provide a task number >:(");
    }

    public void invalidTaskNum() {
        System.out.println("Provide a valid task number >:(");
    }

    public void Delete(int taskNum) {
        System.out.println("Got it. I've removed this task:");
        this.taskList.printTask(taskNum);
    }

    public void taskCount() {
        System.out.printf("Now you have %d tasks in the list.\n", this.taskList.taskCount());
    }

    public void Mark(int taskNum) {
        System.out.println("Marked this task as done:");
        this.taskList.printTask(taskNum);
    }

    public void Unmark(int taskNum) {
        System.out.println("Marked this task as not done:");
        this.taskList.printTask(taskNum);
    }

    public void Add(Task task) {
        System.out.println("Added this task:");
        System.out.println(task.toString());
        this.taskCount();
    }

    public void noTaskDesc() {
        System.out.println("Provide a task description >:(");
    }

    public void deadlineInvalid() {
        System.out.println(":( Invalid syntax for the end date and time of the deadline :(");
        System.out.println("Enter: deadline <description> /by DD.MM.YYYY hh:mm");
    }

    public void eventInvalid() {
        System.out.println(":( Invalid syntaxf for the start and end date and time of the event :(");
        System.out.println("Enter: event <description> /from DD.MM.YYYY hh:mm /to DD.MM.YYYY hh:mm");
    }

    public void dateInvalid() {
        System.out.println(":( Invalid syntax for date/time :(");
        System.out.println("Enter: day.month.year hours: minutes - DD.MM.YYYY hh:mm");
    }

    public void Commands() {
        System.out.println(":( Not a valid command :(. Commands:");
        System.out.println("list");
        System.out.println("mark/unmark/delete <task number>");
        System.out.println("todo <description>");
        System.out.println("deadline <description> /by DD.MM.YYYY hh:mm");
        System.out.println("event <description> /from DD.MM.YYYY hh:mm /to DD.MM.YYYY hh:mm");
    }
}

