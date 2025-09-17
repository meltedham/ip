package meat.filestorage;

import meat.inputoutput.Ui;
import meat.tasks.Tasklist;
import meat.tasks.Deadline;
import meat.tasks.Event;
import meat.tasks.Task;
import meat.tasks.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * For reading from and writing to a storage file for tasks.
 * Responsible for creating, modifying, appending, clearing, and
 * transferring tasks from a file.
 */
public class Storage {

    /** File object representing the storage file. */
    private File file; //file object

    /** Path to the storage file. */
    private String path;

    private Ui ui;

    /**
     * Constructs a Storage for the given path.
     *
     * @param path the file path to store tasks
     */
    public Storage(String path, Ui ui)
    {
        assert path != null : "Path for Storage cannot be null";
        this.path = path;
        this.file = new File(this.path);
        this.ui = ui;
    }

    /** Creates the actual file if it does not exist. */
    public void createActualFile() {
        try {
            this.file.createNewFile();
        } catch (IOException e) {
            System.out.println("File could not be created");
        }
    }

    /**
     * Appends a task to the end of the file, preserving previous content.
     *
     * @param task the task to append
     */
    public void appendFile(Task task) {
        assert task != null : "Task to append to file cannot be null";
        try {
            String textToAppend = task.toFile();
            FileWriter fileWriter = new FileWriter(this.path, true); // create a FileWriter in append mode
            fileWriter.write(textToAppend + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Couldn't append text to file: " + e.getMessage());
        }
    }

    /**
     * Updates the file to match the current Tasklist.
     * Clears the file first and writes all tasks in order.
     *
     * @param taskList the task list to save
     */
    public void modifyFile(Tasklist taskList) {
        assert taskList != null : "Tasklist to modify file with cannot be null";
        this.clearFile();
        for (int i = 0; i < taskList.taskCount(); i++) {
            this.appendFile(taskList.getTask(i));
        }
    }

    /** Clears all content in the storage file. */
    public void clearFile() {
        try {
            FileWriter fileWriter = new FileWriter(this.path, false);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Couldn't clear file");
        }
    }

    /**
     * Loads tasks from the file into the list.
     *
     * @param taskList the list to add the tasks from the file
     */
    public void fileToList(Tasklist taskList) {
        assert taskList != null : "Tasklist to write to cannot be null";
        try {
            Path path = Paths.get(this.path);
            List<String> stringList = Files.readAllLines(path);
            for (int i = 0; i < stringList.size(); i++) {
                String line = stringList.get(i);
                String[] details = line.split("\\|");
                switch (details.length) {
                    case 3: //Todo
                        Todo todo = new Todo(details[2]);
                        mark(details[1], todo);
                        taskList.add(todo);
                        break;
                    case 4: //Deadline
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                        LocalDateTime endDateTime = LocalDateTime.parse(details[3], formatter);
                        Deadline deadline = new Deadline(details[2], endDateTime);
                        mark(details[1], deadline);
                        taskList.add(deadline);
                        break;
                    case 5: //Event
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                        LocalDateTime end = LocalDateTime.parse(details[3], format);
                        LocalDateTime start = LocalDateTime.parse(details[4], format);
                        Event event = new Event(details[2], end, start);
                        mark(details[1], event);
                        taskList.add(event);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Couldn't read file" + e.getMessage());
            this.createActualFile();
        }
    }

    /**
     * Checks if a task should be marked as done, based on the input
     *
     * @param input the string which indicates if the task should be marked as done
     */
    public void mark(String input, Task task) {
        if (input.equals("[X]")) {
            task.mark();
        }
    }

    //Ai-Assisted: ChatGPT, created this method for error handling, which was modified for use
    /**
     * Checks if the storage file can be accessed for reading or writing.
     * Prints an error message if access is denied.
     *
     * @return an error message String if the file is unaccessible, else a greeting message from Ui
     */
    public String validateFileAccess() {
        try {
            if (!this.file.exists()) {
                return "Error: File does not exist: " + file.getPath();
            }
            if (!this.file.canRead()) {
                return "Error: File cannot be read: " + file.getPath();
            }
            if (!this.file.canWrite()) {
                return "Error: File cannot be written: " + file.getPath();
            } else {
                return ui.start("Meat");
            }
        } catch (SecurityException e) {
            return "Error: Access to file denied due to security restrictions: " + file.getPath();
        }
    }
}