package meat.tasks;

/**
 * Interface for tasks. Todo implements Task.
 *
 */
public interface Task {
    public abstract void Mark();

    public abstract void Unmark();

    public abstract String Type();

    public abstract String Marked();

    public abstract String Name();

    public abstract String toString();

    public abstract String toFile();
}
