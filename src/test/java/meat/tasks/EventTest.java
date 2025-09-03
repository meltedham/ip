package meat.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

/** JUnit test class for the Event class */
public class EventTest {

    /** Tests that the task name, type, start, and end date/time are correct. */
    @Test
    void testStartEndAndType() {
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 5, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 9, 5, 17, 0);
        Event event = new Event("Meeting", endTime, startTime);
        assertEquals("Meeting", event.Name());
        assertEquals("[E]", event.Type());
        assertEquals("05.09.2025 09:00", event.Start());
        assertEquals("05.09.2025 17:00", event.End());
    }

    /** Tests the string and file representation of an Event task,
     * and marking the task as done.
     */
    @Test
    void testToStringAndToFile() {
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 5, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 9, 5, 17, 0);
        Event event = new Event("Meeting", endTime, startTime);
        event.Mark();
        String expectedString = "[E][X] Meeting(from: 05.09.2025 09:00 to: 05.09.2025 17:00)";
        String expectedFile = "[E]|[X]|Meeting|05.09.2025 17:00|05.09.2025 09:00";
        assertEquals(expectedString, event.toString());
        assertEquals(expectedFile, event.toFile());
    }
}
