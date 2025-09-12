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
     */
    public Tasklist() {
        this.list = new ArrayList<Task>();
    }

    /**
     * Returns the Task at the specified index.
     *
     * @param i the index of the task
     * @return the Task object at the index
     */
    public Task getTask(int i) {
        assert i >= 0 && i < list.size() : "Invalid index for getTask";
        return this.list.get(i);
    }

    /**
     * Marks a task as done.
     *
     * @param taskNum the 1-based task number
     */
    public void mark(int taskNum) {
        assert taskNum > 0 && taskNum <= list.size() : "Invalid task number for mark";
        list.get(taskNum - 1).mark();
    }

    /**
     * Unmarks a task (sets it as not done).
     *
     * @param taskNum the 1-based task number
     */
    public void unmark(int taskNum) {
        assert taskNum > 0 && taskNum <= list.size() : "Invalid task number for unmark";
        list.get(taskNum - 1).unmark();
    }

    /**
     * Deletes a task from the list.
     *
     * @param taskNum the 1-based task number
     */
    public void delete(int taskNum) {
        assert taskNum > 0 && taskNum <= list.size() : "Invalid task number for delete";
        list.remove(taskNum - 1);
    }

    /**
     * Adds a task to the list.
     *
     * @param task the Task object to add
     */
    public void add(Task task) {
        assert task != null : "Cannot add a null task";
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
     *
     * @return A string representing the list of tasks
     */
    public String printList() {
        String list = "";
        for (int i = 0; i < this.list.size(); i++) {
            list = list + (i + 1) + ". " + this.list.get(i).toString() + "\n";
        };
        return list;
    }

    /**
     * Prints a single task by its number.
     *
     * @param taskNum the 1-based task number
     * @return A String representing the task
     */
    public String printTask(int taskNum) {
        assert taskNum > 0 && taskNum <= list.size() : "Invalid task number for printTask";
        return this.list.get(taskNum - 1).toString();
    }

    /**
     * Finds and prints all the tasks in the list which contain the keyword.
     *
     * @param keyword the keyword to search by
     * @return A String representing the tasks that contain the keyword
     */
    public String printByKeyword(String keyword) {
        assert keyword != null : "find keyword cannot be null";
        int count = 1;
        String list = "";
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).hasKeyword(keyword)) {
                list = list + count + ". " + this.list.get(i).toString() + "\n";
                count++;
            }
        }
        return list;
    }

    /**
     * Finds and prints all the tasks in the list which are on a particular date.
     *
     * @param date the keyword to search by
     * @return A String representing the tasks that contain the date
     */
    public String printByDate(String date) {
        assert date != null;
        int count = 1;
        String list = "";
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).onDate(date)) {
                list = list + count + ". " + this.list.get(i).toString() + "\n";
                count++;
            }
        }
        return list;
    }
}
