package meat.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    void testNameType() {
    Todo todo = new Todo("Read book");
    assertEquals("Read book", todo.Name());
    assertEquals("[T]", todo.Type());
    }

    @Test
    void testMarkUnmark() {
        Todo todo = new Todo("Read book");
        assertEquals("[ ]", todo.Marked());
        todo.Mark();
        assertEquals("[X]", todo.Marked());
        todo.Unmark();
        assertEquals("[ ]", todo.Marked());
    }

    @Test
    void testToStringToFile() {
        Todo todo = new Todo("Walk dog");
        String expectedString = "[T][ ] Walk dog";
        String expectedFile = "[T]|[ ]|Walk dog";
        assertEquals(expectedString, todo.toString());
        assertEquals(expectedFile, todo.toFile());
    }
}
