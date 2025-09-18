package meat.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;

/**
 * JUnit test class for the Tasklist class
 * */
public class TasklistTest {

    /** Tests adding a task to the Tasklist and checking task count. */
    @Test
    void testAddTaskCount() {
        Tasklist taskList = new Tasklist();
        Todo todo = new Todo("Task 1");

        taskList.add(todo);

        assertEquals(1, taskList.taskCount());
        assertEquals(todo, taskList.getTask(0));
    }

    /**
     * Tests deleting a task from the Tasklist and checking task count. */
    @Test
    void testDeleteTaskCount() {
        Tasklist taskList = new Tasklist();
        Todo todo1 = new Todo("Task 1");
        Todo todo2 = new Todo("Task 2");

        taskList.add(todo1);
        taskList.add(todo2);
        assertEquals(2, taskList.taskCount());

        taskList.delete(1);
        assertEquals(1, taskList.taskCount());
        assertEquals(todo2, taskList.getTask(0));
    }

    /** Tests marking and unmarking a task in the Tasklist. */
    @Test
    void testMarkUnmark() {
        Tasklist taskList = new Tasklist();
        Todo todo = new Todo("Read Book");
        taskList.add(todo);
        assertEquals("[ ]", todo.marked());

        taskList.mark(1);
        assertEquals("[X]", todo.marked());

        taskList.unmark(1);
        assertEquals("[ ]", todo.marked());
    }

    /** Tests retrieving a task by index from the Tasklist. */
    @Test
    void testGet() {
        Tasklist taskList = new Tasklist();
        Todo todo = new Todo("Read Book");
        taskList.add(todo);

        assertEquals(todo, taskList.getTask(0));
    }

    /**
     * Tests adding different types of tasks to the Tasklist,
     * checking the type of each task and task count.
     */
    @Test
    void testAddTaskTypes() {
        Tasklist taskList = new Tasklist();
        Todo todo = new Todo("Read Book");
        Deadline deadline = new Deadline("Submit report", LocalDateTime.of(2025, 9, 5, 18, 30));
        Event event = new Event("Meeting", LocalDateTime.of(2025, 9, 5, 17, 0), LocalDateTime.of(2025, 9, 5, 9, 0));

        taskList.add(todo);
        taskList.add(deadline);
        taskList.add(event);

        assertEquals(3, taskList.taskCount());
        assertTrue(taskList.getTask(0) instanceof Todo);
        assertTrue(taskList.getTask(1) instanceof Deadline);
        assertTrue(taskList.getTask(2) instanceof Event);
    }

    /** Tests printing all tasks in the list. */
    @Test
    void testPrintList() {
        Tasklist taskList = new Tasklist();
        Todo todo = new Todo("Read Book");
        taskList.add(todo);

        String expected = "1. [T][ ] Read Book\n";
        assertEquals(expected, taskList.printList());
    }

    /** Tests printing a single task by number. */
    @Test
    void testPrintTask() {
        Tasklist taskList = new Tasklist();
        Todo todo = new Todo("Read Book");
        taskList.add(todo);

        String expected = "[T][ ] Read Book";
        assertEquals(expected, taskList.printTask(1));
    }

    /** Tests finding tasks by keyword. */
    @Test
    void testPrintByKeyword() {
        Tasklist taskList = new Tasklist();
        Todo todo1 = new Todo("Read Book");
        Todo todo2 = new Todo("Write Essay");
        taskList.add(todo1);
        taskList.add(todo2);

        String expected = "1. [T][ ] Read Book\n";
        assertEquals(expected, taskList.printByKeyword("Read"));
    }

    /** Tests finding tasks by date. */
    @Test
    void testPrintByDate() {
        Tasklist taskList = new Tasklist();
        Deadline deadline = new Deadline("Submit report", LocalDateTime.of(2025, 9, 5, 18, 30));
        Event event = new Event("Meeting", LocalDateTime.of(2025, 9, 5, 17, 0), LocalDateTime.of(2025, 9, 5, 9, 0));
        taskList.add(deadline);
        taskList.add(event);

        String expected = "1. [D][ ] Submit report (by: 05.09.2025 18:30)\n"
                + "2. [E][ ] Meeting (from: 05.09.2025 09:00 to: 05.09.2025 17:00)\n";
        assertEquals(expected, taskList.printByDate("05.09.2025"));
    }

    /** Tests detecting duplicate tasks. */
    @Test
    void testHasDuplicate() {
        Tasklist taskList = new Tasklist();
        Todo todo1 = new Todo("Read Book");
        Todo todo2 = new Todo("Read Book");
        Deadline deadline1 = new Deadline("Submit report", LocalDateTime.of(2025, 9, 5, 18, 30));
        Deadline deadline2 = new Deadline("Submit report", LocalDateTime.of(2025, 9, 5, 18, 30));
        taskList.add(todo1);
        taskList.add(deadline1);

        assertTrue(taskList.hasDuplicate(todo2));
        assertTrue(taskList.hasDuplicate(deadline2));

        Event event1 = new Event("Meeting", LocalDateTime.of(2025, 9, 5, 17, 0),
                LocalDateTime.of(2025, 9, 5, 9, 0));
        Event event2 = new Event("Meeting", LocalDateTime.of(2025, 9, 5, 17, 0),
                LocalDateTime.of(2025, 9, 5, 9, 0));
        taskList.add(event1);
        assertTrue(taskList.hasDuplicate(event2));

        Todo uniqueTodo = new Todo("Write Essay");
        assertFalse(taskList.hasDuplicate(uniqueTodo));
    }
}
