package meat.tasks;

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

    public String Name() {
        return this.name;
    }

    public String toString() {
        return this.Type() + this.Marked() + " " + this.Name();
    }

    public String toFile() {
        return this.Type() + "|" + this.Marked() + "|" + this.name;
    }
}
