package meat.inputoutput;

import meat.filestorage.Storage;
import meat.tasks.Tasklist;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    /** Tests parsing and adding Todo tasks */
    @Test
    void testTodo() {
        Tasklist taskList = new Tasklist();
        Ui ui = new Ui(taskList);
        Storage storage = new Storage("test.txt", ui);
        Parser parser = new Parser(ui, taskList, storage);

        String result1 = parser.checkAnyValid("todo Read Book");
        assertEquals("Added this task:\n" +
                "[T][ ] Read Book\n" +
                "Now you have 1 tasks in the list.\n", result1);

        String result2 = parser.checkAnyValid("todo   Write Essay  ");
        assertEquals("Added this task:\n" +
                "[T][ ] Write Essay\n" +
                "Now you have 2 tasks in the list.\n", result2);
    }

    /** Tests parsing and adding Deadline tasks */
    @Test
    void testDeadline() {
        Tasklist taskList = new Tasklist();
        Ui ui = new Ui(taskList);
        Storage storage = new Storage("test.txt", ui);
        Parser parser = new Parser(ui, taskList, storage);

        String validDeadline = "deadline Submit report /by: 05.09.2025 18:30";
        String result = parser.checkAnyValid(validDeadline);

        assertEquals("Added this task:\n" +
                "[D][ ] Submit report (by: 05.09.2025 18:30)\n" +
                "Now you have 1 tasks in the list.\n", result);

        String invalidDeadline = "deadline Submit report /by 05.09.2025 18:30";
        String resultInvalid = parser.checkAnyValid(invalidDeadline);
        assertEquals("Invalid syntax for the deadline command :(\n" +
                "Enter: deadline <description> /by: DD.MM.YYYY hh:mm", resultInvalid);
    }

    /** Tests parsing and adding Event tasks */
    @Test
    void testEvent() {
        Tasklist taskList = new Tasklist();
        Ui ui = new Ui(taskList);
        Storage storage = new Storage("test.txt", ui);
        Parser parser = new Parser(ui, taskList, storage);

        String validEvent = "event Meeting /from: 05.09.2025 09:00 /to: 05.09.2025 17:00";
        String result = parser.checkAnyValid(validEvent);

        assertEquals("Added this task:\n" +
                "[E][ ] Meeting (from: 05.09.2025 09:00 to: 05.09.2025 17:00)\n" +
                "Now you have 1 tasks in the list.\n", result);

        String invalidEvent = "event Meeting /from: 05.09.2025 17:00 /to: 05.09.2025 09:00";
        String resultInvalid = parser.checkAnyValid(invalidEvent);
        assertEquals("The end date/time must be after the start date/time :(", resultInvalid);
    }

    /** Tests mark, unmark, delete commands */
    @Test
    void testMarkUnmarkDelete() {
        Tasklist taskList = new Tasklist();
        Ui ui = new Ui(taskList);
        Storage storage = new Storage("test.txt", ui);
        Parser parser = new Parser(ui, taskList, storage);

        parser.checkAnyValid("todo Read Book");

        String markResult = parser.checkAnyValid("mark 1");
        assertEquals("Marked this task as done:\n[T][X] Read Book", markResult);

        String unmarkResult = parser.checkAnyValid("unmark 1");
        assertEquals("Marked this task as not done:\n[T][ ] Read Book", unmarkResult);

        String deleteResult = parser.checkAnyValid("delete 1");
        assertEquals("Got it. I've removed this task:\n[T][ ] Read Book\nNow you have 0 tasks in the list.\n", deleteResult);

        String invalidMark = parser.checkAnyValid("mark 99");
        assertEquals("Provide a valid task number >:(", invalidMark);
    }

    /** Tests find command */
    @Test
    void testFind() {
        Tasklist taskList = new Tasklist();
        Ui ui = new Ui(taskList);
        Storage storage = new Storage("test.txt", ui);
        Parser parser = new Parser(ui, taskList, storage);

        parser.checkAnyValid("todo Read Book");
        parser.checkAnyValid("todo Write Essay");

        String findResult = parser.checkAnyValid("find Book");
        assertTrue(findResult.contains("Here are the matching tasks in your list:"));

        String invalidFind = parser.checkAnyValid("find");
        assertEquals("Provide a keyword to search by >:(", invalidFind);
    }

    /** Tests schedule command */
    @Test
    void testSchedule() {
        Tasklist taskList = new Tasklist();
        Ui ui = new Ui(taskList);
        Storage storage = new Storage("test.txt", ui);
        Parser parser = new Parser(ui, taskList, storage);

        parser.checkAnyValid("deadline Submit report /by: 05.09.2025 18:30");
        parser.checkAnyValid("todo Write Essay");

        String scheduleResult = parser.checkAnyValid("schedule 05.09.2025");
        assertTrue(scheduleResult.contains("Here's your schedule for 05.09.2025:"));

        String invalidSchedule = parser.checkAnyValid("schedule 2025-09-05");
        assertEquals("Invalid syntax for the schedule command :(\nEnter: schedule DD.MM.YYYY", invalidSchedule);
    }
}
