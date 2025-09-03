package meat.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

/** Junit test class for the Deadline class */
public class DeadlineTest {

    /** Tests that the task name, type, and end date/time are correct. */
    @Test
    void testEndType() {
        LocalDateTime deadlineTime = LocalDateTime.of(2025, 9, 5, 18, 30);
        Deadline deadline = new Deadline("Submit report", deadlineTime);
        assertEquals("Submit report", deadline.Name());
        assertEquals("[D]", deadline.Type());
        assertEquals("05.09.2025 18:30", deadline.End());
    }

    /** Tests the string and file representation of a Deadline task. */
    @Test
    void testToStringToFile() {
        LocalDateTime deadlineTime = LocalDateTime.of(2025, 9, 5, 18, 30);
        Deadline deadline = new Deadline("Submit report", deadlineTime);
        String expectedString = "[D][ ] Submit report(by: 05.09.2025 18:30)";
        String expectedFile = "[D]|[ ]|Submit report|05.09.2025 18:30";
        assertEquals(expectedString, deadline.toString());
        assertEquals(expectedFile, deadline.toFile());
    }
}
