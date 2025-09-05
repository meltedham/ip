package meat.filestorage;

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

import java.util.ArrayList;
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

    /**
     * Constructs a Storage for the given path.
     *
     * @param path the file path to store tasks
     */
    public Storage(String path)
    {
        this.path = path;
        this.file = new File(this.path);
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
     * Writes a task to the file, overwriting any existing content.
     *
     * @param task the task to write
     */
    public void writeFile(Task task) {
        try {
            String textToAdd = task.toFile();
            FileWriter fileWriter = new FileWriter(this.path);
            fileWriter.write(textToAdd + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Couldn't add text to file: " + e.getMessage());
        }
    }

    /**
     * Appends a task to the end of the file, preserving previous content.
     *
     * @param task the task to append
     */
    public void appendFile(Task task) {
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
     * @param list the list to add the tasks from the file
     */
    public void fileToList(Tasklist list) {
        try {
            System.out.println("Working directory: " + System.getProperty("user.dir"));

            Path path = Paths.get(this.path);
            List<String> stringList = Files.readAllLines(path);
            for (int i = 0; i < stringList.size(); i++) {
                String line = stringList.get(i);
                String[] details = line.split("\\|");
                switch (details.length) {
                    case 3: //Todo
                        Todo todo = new Todo(details[2]);
                        if (details[1].equals("[X]")) {
                            todo.mark();
                        }
                        list.add(todo);
                        break;
                    case 4: //Deadline
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                        LocalDateTime endDateTime = LocalDateTime.parse(details[3], formatter);
                        Deadline deadline = new Deadline(details[2], endDateTime);
                        if (details[1].equals("[X]")) {
                            deadline.mark();
                        }
                        list.add(deadline);
                        break;
                    case 5: //Event
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                        LocalDateTime end = LocalDateTime.parse(details[3], format);
                        LocalDateTime start = LocalDateTime.parse(details[4], format);
                        Event event = new Event(details[2], end, start);
                        if (details[1].equals("[X]")) {
                            event.mark();
                        }
                        list.add(event);
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

}