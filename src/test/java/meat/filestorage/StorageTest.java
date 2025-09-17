package meat.filestorage;

import meat.inputoutput.Ui;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import meat.tasks.Todo;
import meat.tasks.Deadline;
import meat.tasks.Tasklist;
import meat.tasks.Task;

import java.io.File;
import java.io.IOException;

import java.time.LocalDateTime;

import java.util.ArrayList;

/**
 * JUnit test class for the Storage class
 * */
public class StorageTest {

    /** Path to file created for testing. */
    private static final String TEST_FILE = "test_storage.txt";
    private final Tasklist taskList = new Tasklist();
    private Ui ui = new Ui(taskList);

    /** Tests creating and clearing of a file. */
    @Test
    void testCreateClearFile() throws IOException {
        Storage storage = new Storage(TEST_FILE, ui);
        storage.createActualFile();

        File file = new File(TEST_FILE);
        assertEquals(true, file.exists());

        storage.clearFile();
        assertEquals(0, file.length());

        file.delete();
    }

    /**
     * Tests writing a task to the file and reading it back into a list.
     */
    @Test
    void testWriteFileFileToList() throws IOException {
        Storage storage = new Storage(TEST_FILE, ui);
        storage.clearFile();

        Todo todo = new Todo("Read Book");
        storage.appendFile(todo);

        Tasklist tasklist = new Tasklist();
        storage.fileToList(tasklist);

        assertEquals(1, tasklist.taskCount());
        assertEquals("Read Book", tasklist.getTask(0).name());
        assertEquals(false, tasklist.getTask(0).marked().equals("[X]"));
        new File(TEST_FILE).delete();
    }

    /**
     * Tests appending multiple tasks to a file and reading them back.
     */
    @Test
    void testAppendFile() throws IOException {
        Storage storage = new Storage(TEST_FILE, ui);
        storage.clearFile();

        Todo todo1 = new Todo("Task 1");
        Todo todo2 = new Todo("Task 2");
        storage.appendFile(todo1);
        storage.appendFile(todo2);

        Tasklist tasklist = new Tasklist();
        storage.fileToList(tasklist);

        assertEquals(2, tasklist.taskCount());
        assertEquals("Task 1", tasklist.getTask(0).name());
        assertEquals("Task 2", tasklist.getTask(1).name());

        new File(TEST_FILE).delete();
    }

    /**
     * Tests modifying a file to reflect a Tasklist with multiple task types.
     */
    @Test
    void testModifyFile() throws IOException {
        Storage storage = new Storage(TEST_FILE, ui);
        storage.clearFile();

        ArrayList<Task> tasks = new ArrayList<>();
        Tasklist tasklist = new Tasklist();
        Todo todo = new Todo("Read Book");
        Deadline deadline = new Deadline("Submit report", LocalDateTime.of(2025, 9, 5, 18, 30));
        tasklist.add(todo);
        tasklist.add(deadline);

        storage.modifyFile(tasklist);

        //ArrayList<Task> list = new ArrayList<>();
        storage.fileToList(tasklist);

        assertEquals(2, tasklist.taskCount());
        assertEquals("Read Book", tasklist.getTask(0).name());
        assertEquals("Submit report", tasklist.getTask(1).name());

        new File(TEST_FILE).delete();
    }

}
