package meat.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;

public class DeadlineTest {
    @Test
    void testEndType() {
        LocalDateTime deadlineTime = LocalDateTime.of(2025, 9, 5, 18, 30);
        Deadline deadline = new Deadline("Submit report", deadlineTime);
        assertEquals("Submit report", deadline.Name());
        assertEquals("[D]", deadline.Type());
        assertEquals("05.09.2025 18:30", deadline.End());
    }

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
