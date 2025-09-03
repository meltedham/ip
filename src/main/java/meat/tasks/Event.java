package meat.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that occurs over a period of time.
 * Extends Deadline by adding a start date and time.
 */
public class Event extends Deadline {

    /** The start date and time of the event. */
    private LocalDateTime start;

    /**
     * Constructs an Event with a name, end date/time, and start date/time.
     *
     * @param name  the name of the event
     * @param end   the LocalDateTime representing the end of the event
     * @param start the LocalDateTime representing the start of the event
     */
    public Event(String name, LocalDateTime end, LocalDateTime start) {
        super(name, end);
        this.start = start;
    }

    /**
     * Returns the start date and time formatted as "dd.MM.yyyy HH:mm".
     *
     * @return the formatted start date/time
     */
    public String Start() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return this.start.format(formatter);
    }

    /**
     * Returns a string representation of the Event, with its
     * type, marked status, name, start, and end date/time.
     *
     * @return string representation of the Event
     */
    @Override
    public String toString() {
        return Type() + Marked() + " " + Name() + "(from: " + this.Start() + " " + "to: " + End() + ")";
    }

    /**
     * Returns a string representation of the Event for file storage.
     * Format: type|marked|name|end|start
     *
     * @return string for file storage
     */
    @Override
    public String toFile() {
        return Type() + "|" + Marked() + "|" + Name() + "|" + End() + "|" + this.Start();
    }
}
