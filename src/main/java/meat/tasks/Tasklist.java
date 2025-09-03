package meat.tasks;

import java.util.ArrayList;

/**
 * Represents a list of tasks. Has methods to mark/unmark, delete,
 * add, access, and print tasks.
 */

public class Tasklist {

    /** An ArrayList of tasks. */
    private ArrayList<Task> list;

    /**
     * Constructs a Tasklist with an initial list of tasks.
     *
     * @param tasks the initial list of Task objects
     */
    public Tasklist(ArrayList<Task> tasks) {
        this.list = tasks;
    }

    /**
     * Returns the Task at the specified index.
     *
     * @param i the index of the task
     * @return the Task object at the index
     */
    public Task getTask(int i) {
        return this.list.get(i);
    }

    /**
     * Marks a task as done.
     *
     * @param taskNum the 1-based task number
     */
    public void Mark(int taskNum) {
        list.get(taskNum - 1).Mark();
    }

    /**
     * Unmarks a task (sets it as not done).
     *
     * @param taskNum the 1-based task number
     */
    public void Unmark(int taskNum) {
        list.get(taskNum - 1).Unmark();
    }

    /**
     * Deletes a task from the list.
     *
     * @param taskNum the 1-based task number
     */
    public void Delete(int taskNum) {
        list.remove(taskNum - 1);
    }

    /**
     * Adds a task to the list.
     *
     * @param task the Task object to add
     */
    public void Add(Task task) {
        list.add(task);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return task count
     */
    public int taskCount() {
        return this.list.size();
    }

    /**
     * Prints all tasks in the list to the console, numbered sequentially.
     */
    public void printList() {
        for (int i = 0; i < this.list.size(); i++) {
            System.out.println((i + 1) + ". " + this.list.get(i).toString());
        };
    }

    /**
     * Prints a single task by its number.
     *
     * @param taskNum the 1-based task number
     */
    public void printTask(int taskNum) {
        System.out.println(this.list.get(taskNum - 1).toString());
    }
}
