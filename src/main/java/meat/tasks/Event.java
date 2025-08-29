package meat.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Deadline {
    private LocalDateTime start;

    public Event(String name, LocalDateTime end, LocalDateTime start) {
        super(name, end);
        this.start = start;
    }

    public String Start() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return this.start.format(formatter);
    }

    @Override
    public String toString() {
        return Type() + Marked() + " " + Name() + "(from: " + this.Start() + " " + "to: " + End() + ")";
    }

    @Override
    public String toFile() {
        return Type() + "|" + Marked() + "|" + Name() + "|" + End() + "|" + this.Start();
    }
}
