public class Event extends Deadline {
    private String start;

    public Event(String name, String end, String start) {
        super(name, end);
        this.start = start;
    }

    @Override
    public String toString() {
        String line = Name(this) + "(from: " + this.start + "to: " + End() + ")";
        return line;
    }
}
