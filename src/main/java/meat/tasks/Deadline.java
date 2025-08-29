package meat.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Todo {
    //private String end;
    private LocalDateTime end;

    public Deadline(String name, LocalDateTime end) {
        super(name);
        this.end = end;
    }

    public String End() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return this.end.format(formatter);
    }

    @Override
    public String toString() {
        return Type() + Marked() + " " + Name() + "(by: " + this.End() + ")";
    }

    @Override
    public String toFile() {
        return Type() + "|" + Marked() + "|" + Name() + "|" + this.End();
    }
}
