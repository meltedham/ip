package meat.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;

/**
 * JUnit test class for the Event class
 * */
public class EventTest {

    /** Tests that the task name, type, start, and end date/time are correct. */
    @Test
    void testStartEndAndType() {
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 5, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 9, 5, 17, 0);

        Event event = new Event("Meeting", endTime, startTime);

        assertEquals("Meeting", event.name());
        assertEquals("[E]", event.type());
        assertEquals("05.09.2025 09:00", event.start());
        assertEquals("05.09.2025 17:00", event.end());
    }

    /** Tests the string and file representation of an Event task,
     * and marking the task as done.
     */
    @Test
    void testToStringAndToFile() {
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 5, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 9, 5, 17, 0);

        Event event = new Event("Meeting", endTime, startTime);
        event.mark();

        String expectedString = "[E][X] Meeting (from: 05.09.2025 09:00 to: 05.09.2025 17:00)";
        String expectedFile = "[E]|[X]|Meeting|05.09.2025 17:00|05.09.2025 09:00";

        assertEquals(expectedString, event.toString());
        assertEquals(expectedFile, event.toFile());
    }

    /** Tests marking and unmarking an Event task. */
    @Test
    void testMarkUnmark() {
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 5, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 9, 5, 17, 0);

        Event event = new Event("Conference", endTime, startTime);

        assertEquals("[ ]", event.marked());

        event.mark();
        assertEquals("[X]", event.marked());

        event.unmark();
        assertEquals("[ ]", event.marked());
    }

    /** Tests if an Event task contains a keyword in its name. */
    @Test
    void testHasKeyword() {
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 5, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 9, 5, 17, 0);

        Event event = new Event("Team Meeting", endTime, startTime);

        assertTrue(event.hasKeyword("Team"));
        assertTrue(event.hasKeyword("Meeting"));
        assertFalse(event.hasKeyword("Workshop"));
    }

    /** Tests if an Event task occurs on a given date. */
    @Test
    void testOnDate() {
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 5, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 9, 5, 17, 0);

        Event event = new Event("Team Meeting", endTime, startTime);

        assertTrue(event.onDate("05.09.2025"));
        assertFalse(event.onDate("06.09.2025"));
    }

    /** Tests specifically onStartDate method. */
    @Test
    void testOnStartDate() {
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 5, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 9, 5, 17, 0);

        Event event = new Event("Team Meeting", endTime, startTime);

        assertTrue(event.onStartDate("05.09.2025"));
        assertFalse(event.onStartDate("06.09.2025"));
    }
}
