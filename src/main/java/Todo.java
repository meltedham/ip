public class Todo implements Task {
    private final String name;
    private boolean done;

    public Todo(String name) {
        this.name = name;
        this.done = false;
    }

    public void Mark() {
        this.done = true;
    }

    public void Unmark() {
        this.done = false;
    }

    public String Name(Todo task) {
        String tick = "";
        String TED = "";
        if (task.done)
        {
            tick = "[X]";
        } else {
            tick = "[ ]";
        }
        if (task instanceof Event) {
            TED = "[E]";
        } else if (task instanceof Deadline) {
            TED = "[D]";
        } else {
            TED = "[T]";
        }
        return TED + tick + " " + this.name;
    }

    public String toString() {
        return this.Name(this);
    }
}
