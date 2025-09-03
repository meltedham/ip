package meat.filestorage;

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

    /** Tests creating and clearing of a file. */
    @Test
    void testCreateClearFile() throws IOException {
        Storage storage = new Storage(TEST_FILE);
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
        Storage storage = new Storage(TEST_FILE);
        storage.clearFile();

        Todo todo = new Todo("Read Book");
        storage.writeFile(todo);

        ArrayList<Task> list = new ArrayList<>();
        storage.fileToList(list);

        assertEquals(1, list.size());
        assertEquals("Read Book", list.get(0).Name());
        assertEquals(false, list.get(0).Marked().equals("[X]"));
        new File(TEST_FILE).delete();
    }

    /**
     * Tests appending multiple tasks to a file and reading them back.
     */
    @Test
    void testAppendFile() throws IOException {
        Storage storage = new Storage(TEST_FILE);
        storage.clearFile();

        Todo todo1 = new Todo("Task 1");
        Todo todo2 = new Todo("Task 2");
        storage.appendFile(todo1);
        storage.appendFile(todo2);

        ArrayList<Task> list = new ArrayList<>();
        storage.fileToList(list);

        assertEquals(2, list.size());
        assertEquals("Task 1", list.get(0).Name());
        assertEquals("Task 2", list.get(1).Name());

        new File(TEST_FILE).delete();
    }

    /**
     * Tests modifying a file to reflect a Tasklist with multiple task types.
     */
    @Test
    void testModifyFile() throws IOException {
        Storage storage = new Storage(TEST_FILE);
        storage.clearFile();

        ArrayList<Task> tasks = new ArrayList<>();
        Tasklist taskList = new Tasklist(tasks);
        Todo todo = new Todo("Read Book");
        Deadline deadline = new Deadline("Submit report", LocalDateTime.of(2025, 9, 5, 18, 30));
        taskList.Add(todo);
        taskList.Add(deadline);

        storage.modifyFile(taskList);

        ArrayList<Task> list = new ArrayList<>();
        storage.fileToList(list);

        assertEquals(2, list.size());
        assertEquals("Read Book", list.get(0).Name());
        assertEquals("Submit report", list.get(1).Name());

        new File(TEST_FILE).delete();
    }

}
