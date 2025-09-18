package meat.filestorage;

import meat.inputoutput.Ui;

import meat.tasks.Todo;
import meat.tasks.Deadline;
import meat.tasks.Tasklist;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * JUnit test class for the Storage class.
 */
public class StorageTest {

    /** Path to file created for testing. */
    private static final String TEST_FILE = "test_storage.txt";

    /** Tests creating and clearing of a file. */
    @Test
    void testCreateClearFile() throws IOException {
        Tasklist taskList = new Tasklist();
        Ui ui = new Ui(taskList);
        Storage storage = new Storage(TEST_FILE, ui);

        storage.createActualFile();
        File file = new File(TEST_FILE);
        assertTrue(file.exists());

        storage.clearFile();
        assertEquals(0, file.length());

        file.delete();
    }

    /** Tests writing a task to the file and reading it back into a list. */
    @Test
    void testWriteFileFileToList() throws IOException {
        Tasklist taskList = new Tasklist();
        Ui ui = new Ui(taskList);
        Storage storage = new Storage(TEST_FILE, ui);

        storage.clearFile();

        Todo todo = new Todo("Read Book");
        storage.appendFile(todo);

        Tasklist tasklistFromFile = new Tasklist();
        storage.fileToList(tasklistFromFile);

        assertEquals(1, tasklistFromFile.taskCount());
        assertEquals("Read Book", tasklistFromFile.getTask(0).name());
        assertFalse(tasklistFromFile.getTask(0).marked().equals("[X]"));

        new File(TEST_FILE).delete();
    }

    /** Tests appending multiple tasks to a file and reading them back. */
    @Test
    void testAppendFile() throws IOException {
        Tasklist taskList = new Tasklist();
        Ui ui = new Ui(taskList);
        Storage storage = new Storage(TEST_FILE, ui);

        storage.clearFile();

        Todo todo1 = new Todo("Task 1");
        Todo todo2 = new Todo("Task 2");
        storage.appendFile(todo1);
        storage.appendFile(todo2);

        Tasklist tasklistFromFile = new Tasklist();
        storage.fileToList(tasklistFromFile);

        assertEquals(2, tasklistFromFile.taskCount());
        assertEquals("Task 1", tasklistFromFile.getTask(0).name());
        assertEquals("Task 2", tasklistFromFile.getTask(1).name());

        new File(TEST_FILE).delete();
    }

    /** Tests modifying a file to reflect a Tasklist with multiple task types. */
    @Test
    void testModifyFile() throws IOException {
        Tasklist taskList = new Tasklist();
        Ui ui = new Ui(taskList);
        Storage storage = new Storage(TEST_FILE, ui);

        storage.clearFile();

        Tasklist tasklist = new Tasklist();
        Todo todo = new Todo("Read Book");
        Deadline deadline = new Deadline("Submit report", LocalDateTime.of(2025, 9, 5, 18, 30));
        tasklist.add(todo);
        tasklist.add(deadline);

        storage.modifyFile(tasklist);

        Tasklist tasklistFromFile = new Tasklist();
        storage.fileToList(tasklistFromFile);

        assertEquals(2, tasklistFromFile.taskCount());
        assertEquals("Read Book", tasklistFromFile.getTask(0).name());
        assertEquals("Submit report", tasklistFromFile.getTask(1).name());

        new File(TEST_FILE).delete();
    }
}
