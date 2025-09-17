package meat.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 * Extends Todo by adding an end date and time.
 */
public class Deadline extends Todo {

    /** The end date and time of the deadline. */
    private LocalDateTime end;

    /**
     * Constructs a Deadline with a name and end date/time.
     *
     * @param name the name of the task
     * @param end  the LocalDateTime representing the deadline
     */
    public Deadline(String name, LocalDateTime end) {
        super(name);
        assert end != null : "End time cannot be null";
        this.end = end;
    }

    /**
     * Returns the end date and time formatted as "dd.MM.yyyy HH:mm".
     *
     * @return the formatted end date/time
     */
    public String end() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return this.end.format(formatter);
    }

    /**
     * Returns a string representation of the Deadline, with its
     * type, marked status, name, and formatted end date/time.
     *
     * @return string representation of the Deadline
     */
    @Override
    public String toString() {
        return type() + marked() + " " + name() + " (by: " + this.end() + ")";
    }

    /**
     * Returns a string representation of the Deadline for file storage.
     * Format: type|marked|name|endDateTime
     *
     * @return string for file storage
     */
    @Override
    public String toFile() {
        return type() + "|" + marked() + "|" + name() + "|" + this.end();
    }

    /**
     * Checks if the task ends on a particular date.
     *
     * @param date the date to search by
     * @return returns true if the end date matches the given date, else false
     */
    public boolean onEndDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate dateFormatted = LocalDate.parse(date, formatter);
        if (this.end.toLocalDate().equals(dateFormatted)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calls the function to check if the task ends on a particular date.
     *
     * @param date the date to search by
     * @return returns true if the end date matches the given date, else false
     */
    @Override
    public boolean onDate(String date) {
        return this.onEndDate(date);
    }
}
