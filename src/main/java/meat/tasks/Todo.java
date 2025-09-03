package meat.tasks;

/**
 * Represents a type of task with just a name.
 */
public class Todo implements Task {

    /** The name of the task. */
    private final String name;

    /** Indicates whether the task is done. */
    private boolean done;

    /**
     * Constructs a Todo with a name.
     * The task is not done by default.
     *
     * @param name the task name
     */
    public Todo(String name) {
        this.name = name;
        this.done = false;
    }

    /**
     * Marks the task as done.
     */
    public void Mark() {
        this.done = true;
    }

    /**
     * Marks the task as not done.
     */
    public void Unmark() {
        this.done = false;
    }

    /**
     * Returns the type of the task.
     * "[T]" for Todo, "[D]" for Deadline, "[E]" for Event.
     *
     * @return a string representing the task type
     */
    public String Type() {
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
    public String Marked() {
        String tick = "";
        if (this.done)
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
    public String Name() {
        return this.name;
    }

    /**
     * Returns a string representation of the Todo, with its
     * type, completion status, and name.
     *
     * @return the string representation of the task
     */
    public String toString() {
        return this.Type() + this.Marked() + " " + this.Name();
    }

    /**
     * Returns a string representation of Todo for file storage.
     * Format: Type|Marked|Name
     *
     * @return the string representation of the task for file storage
     */
    public String toFile() {
        return this.Type() + "|" + this.Marked() + "|" + this.name;
    }
}
