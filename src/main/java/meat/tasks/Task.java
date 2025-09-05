package meat.tasks;

/**
 * Interface for tasks. Todo implements Task.
 */
public interface Task {
    public abstract void mark();

    public abstract void unmark();

    public abstract String type();

    public abstract String marked();

    public abstract String name();

    public abstract String toString();

    public abstract String toFile();

    public abstract boolean hasKeyword(String keyword);
}
