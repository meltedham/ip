package meat.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TasklistTest {
    @Test
    void testAddTaskCount() {
        Tasklist taskList = new Tasklist(new ArrayList<>());
        Todo todo = new Todo("Task 1");
        taskList.Add(todo);
        assertEquals(1, taskList.taskCount());
        assertEquals(todo, taskList.getTask(0));
    }

    @Test
    void testDeleteTaskCount() {
        Tasklist taskList = new Tasklist(new ArrayList<>());
        Todo todo1 = new Todo("Task 1");
        Todo todo2 = new Todo("Task 2");
        taskList.Add(todo1);
        taskList.Add(todo2);
        assertEquals(2, taskList.taskCount());
        taskList.Delete(1);
        assertEquals(1, taskList.taskCount());
        assertEquals(todo2, taskList.getTask(0));
    }

    @Test
    void testMarkUnmark() {
        Tasklist taskList = new Tasklist(new ArrayList<>());
        Todo todo = new Todo("Read Book");
        taskList.Add(todo);
        assertEquals("[ ]", todo.Marked());
        taskList.Mark(1);
        assertEquals("[X]", todo.Marked());
        taskList.Unmark(1);
        assertEquals("[ ]", todo.Marked());
    }

    @Test
    void testGet() {
        Tasklist taskList = new Tasklist(new ArrayList<>());
        Todo todo = new Todo("Read Book");
        taskList.Add(todo);
        assertEquals(todo, taskList.getTask(0));
    }

    @Test
    void testAddTaskTypes() {
        Tasklist taskList = new Tasklist(new ArrayList<>());
        Todo todo = new Todo("Read Book");
        Deadline deadline = new Deadline("Submit report", LocalDateTime.of(2025, 9, 5, 18, 30));
        Event event = new Event("Meeting", LocalDateTime.of(2025, 9, 5, 17, 0), LocalDateTime.of(2025, 9, 5, 9, 0));
        taskList.Add(todo);
        taskList.Add(deadline);
        taskList.Add(event);
        assertEquals(3, taskList.taskCount());
        assertEquals(true, taskList.getTask(0) instanceof Todo);
        assertEquals(true, taskList.getTask(1) instanceof Deadline);
        assertEquals(true, taskList.getTask(2) instanceof Event);
    }
}
