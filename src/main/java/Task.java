public class Task {
    private String name;
    private boolean done;

    public Task(String name)
    {
        this.name = name;
        this.done = false;
    }

    public void Mark()
    {
        this.done = true;
    }

    public void Unmark()
    {
        this.done = false;
    }

    public String toString()
    {
        String tick = "";
        if (this.done)
        {
            tick = "[X]";
        } else {
            tick = "[ ]";
        }
        String line = tick + " " + this.name;
        return line;
    }
}
