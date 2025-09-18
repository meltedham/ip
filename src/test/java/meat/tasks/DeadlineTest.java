package meat.tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the Deadline class.
 */
public class DeadlineTest {

    /** Tests that the task name, type, and end date/time are correct. */
    @Test
    void testEndType() {
        LocalDateTime deadlineTime = LocalDateTime.of(2025, 9, 5, 18, 30);
        Deadline deadline = new Deadline("Submit report", deadlineTime);

        assertEquals("Submit report", deadline.name());
        assertEquals("[D]", deadline.type());
        assertEquals("05.09.2025 18:30", deadline.end());
    }

    /** Tests the string and file representation of a Deadline task. */
    @Test
    void testToStringToFile() {
        LocalDateTime deadlineTime = LocalDateTime.of(2025, 9, 5, 18, 30);
        Deadline deadline = new Deadline("Submit report", deadlineTime);

        String expectedString = "[D][ ] Submit report (by: 05.09.2025 18:30)";
        String expectedFile = "[D]|[ ]|Submit report|05.09.2025 18:30";

        assertEquals(expectedString, deadline.toString());
        assertEquals(expectedFile, deadline.toFile());
    }

    /** Tests that onEndDate returns true for matching date. */
    @Test
    void testOnEndDateTrue() {
        LocalDateTime deadlineTime = LocalDateTime.of(2025, 9, 5, 18, 30);
        Deadline deadline = new Deadline("Submit report", deadlineTime);

        assertTrue(deadline.onEndDate("05.09.2025"));
        assertTrue(deadline.onDate("05.09.2025"));
    }

    /** Tests that onEndDate returns false for non-matching date. */
    @Test
    void testOnEndDateFalse() {
        LocalDateTime deadlineTime = LocalDateTime.of(2025, 9, 5, 18, 30);
        Deadline deadline = new Deadline("Submit report", deadlineTime);

        assertFalse(deadline.onEndDate("06.09.2025"));
        assertFalse(deadline.onDate("04.09.2025"));
    }
}
