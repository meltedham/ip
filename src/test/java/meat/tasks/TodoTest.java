package meat.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JUnit test class for the Todo class.
 * */
public class TodoTest {

    /** Tests that the name and type of a Todo are correct. */
    @Test
    void testNameType() {
    Todo todo = new Todo("Read book");

    assertEquals("Read book", todo.name());
    assertEquals("[T]", todo.type());
    }

    /** Tests marking and unmarking a Todo task. */
    @Test
    void testMarkUnmark() {
        Todo todo = new Todo("Read book");
        assertEquals("[ ]", todo.marked());

        todo.mark();
        assertEquals("[X]", todo.marked());

        todo.unmark();
        assertEquals("[ ]", todo.marked());
    }

    /** Tests the string and file representations of a Todo. */
    @Test
    void testToStringToFile() {
        Todo todo = new Todo("Walk dog");

        String expectedString = "[T][ ] Walk dog";
        String expectedFile = "[T]|[ ]|Walk dog";

        assertEquals(expectedString, todo.toString());
        assertEquals(expectedFile, todo.toFile());
    }
}
