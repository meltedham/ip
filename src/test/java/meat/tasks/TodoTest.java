package meat.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

    /** Tests if a Todo task contains a keyword. */
    @Test
    void testHasKeyword() {
        Todo todo = new Todo("Read book");

        assertTrue(todo.hasKeyword("Read"));
        assertTrue(todo.hasKeyword("book"));
        assertFalse(todo.hasKeyword("Walk"));
    }

    /** Tests that onDate always returns false for a Todo. */
    @Test
    void testOnDate() {
        Todo todo = new Todo("Read book");

        assertFalse(todo.onDate("05.09.2025"));
        assertFalse(todo.onDate("01.01.2000"));
    }
}
