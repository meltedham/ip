package meat.tasks;

/**
 * Represents a type of task with just a name.
 */
public class Todo implements Task {

    /** The name of the task. */
    private final String name;

    /** Indicates whether the task is done. */
    private boolean isDone;

    /**
     * Constructs a Todo with a name.
     * The task is not done by default.
     *
     * @param name the task name
     */
    public Todo(String name) {
        assert name != null: "Task name cannot be null";
        this.name = name;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns the type of the task.
     * "[T]" for Todo, "[D]" for Deadline, "[E]" for Event.
     *
     * @return a string representing the task type
     */
    public String type() {
        String TED = "";
        if (this instanceof Event) {
            TED = "[E]";
        } else if (this instanceof Deadline) {
            TED = "[D]";
        } else {
            TED = "[T]";
        }
        return TED;
    }

    /**
     * Returns a string representing whether the task is done.
     * "[X]" if done, "[ ]" if not done.
     *
     * @return a string representing the completion status
     */
    public String marked() {
        String tick = "";
        if (this.isDone)
        {
            tick = "[X]";
        } else {
            tick = "[ ]";
        }
        return tick;
    }

    /**
     * Returns the name of the task.
     *
     * @return the task name
     */
    public String name() {
        return this.name;
    }

    /**
     * Returns a string representation of the Todo, with its
     * type, completion status, and name.
     *
     * @return the string representation of the task
     */
    public String toString() {
        return this.type() + this.marked() + " " + this.name();
    }

    /**
     * Returns a string representation of Todo for file storage.
     * Format: Type|Marked|Name
     *
     * @return the string representation of the task for file storage
     */
    public String toFile() {
        return this.type() + "|" + this.marked() + "|" + this.name;
    }

    /**
     * Checks if the task name contains the keyword.
     *
     * @param keyword the keyword to search by
     * @return true if the name contains the keyword, else false
     */
    public boolean hasKeyword(String keyword) {
        assert keyword != null : "find keyword cannot be null";
        if (this.name.contains(keyword)) {
            return true;
        } else {
            return false;
        }
    }
}
