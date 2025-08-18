public interface Task {
    public abstract void Mark();

    public abstract void Unmark();

    public abstract String Name(Todo task);

    public abstract String toString();
}
