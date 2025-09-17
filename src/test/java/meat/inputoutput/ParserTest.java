package meat.inputoutput;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import meat.filestorage.Storage;

import meat.tasks.Todo;
import meat.tasks.Deadline;
import meat.tasks.Event;
import meat.tasks.Tasklist;

import java.util.ArrayList;

/**
 * Junit test class for the Parser class
 * */
public class ParserTest {

    /** Tests parsing and adding a Todo task. */
    @Test
    void testTodo() {
        Tasklist taskList = new Tasklist();
        Ui ui = new Ui(taskList);
        Storage storage = new Storage("test.txt", ui);
        Parser parser = new Parser(ui, taskList, storage);

        parser.checkAnyValid("todo Read Book");

        assertEquals(1, taskList.taskCount());
        assertEquals("Read Book", taskList.getTask(0).name());
        assertEquals(true, taskList.getTask(0) instanceof Todo);
    }

    /** Tests parsing and adding a Deadline task. */
    @Test
    void testDeadline() {
        Tasklist taskList = new Tasklist();
        Ui ui = new Ui(taskList);
        Storage storage = new Storage("test.txt", ui);
        Parser parser = new Parser(ui, taskList, storage);

        parser.checkAnyValid("deadline Submit report /by: 05.09.2025 18:30");

        assertEquals(1, taskList.taskCount());
        assertEquals("Submit report", taskList.getTask(0).name());
        assertEquals("05.09.2025 18:30", ((Deadline) taskList.getTask(0)).end());
        assertEquals(true, taskList.getTask(0) instanceof Deadline);
    }

    /** Tests parsing and adding an Event task. */
    @Test
    void testEvent() {
        Tasklist taskList = new Tasklist();
        Ui ui = new Ui(taskList);
        Storage storage = new Storage("test.txt", ui);
        Parser parser = new Parser(ui, taskList, storage);

        parser.checkAnyValid("event Meeting /from: 05.09.2025 09:00 /to: 05.09.2025 17:00");

        assertEquals(1, taskList.taskCount());
        assertEquals("Meeting", taskList.getTask(0).name());
        assertEquals("05.09.2025 09:00", ((Event) taskList.getTask(0)).start());
        assertEquals("05.09.2025 17:00", ((Event) taskList.getTask(0)).end());
        assertEquals(true, taskList.getTask(0) instanceof Event);
    }


    /**
     * Tests the method that checks for valid date/time formatting.
     */
    @Test
    void testIsInvalidDate() {
        Tasklist taskList = new Tasklist();
        Ui ui = new Ui(taskList);
        Storage storage = new Storage("test.txt", ui);
        Parser parser = new Parser(ui, taskList, storage);

        assertEquals(false, parser.isDateValid("31-02-2025 25:00")); // clearly invalid
    }
}
