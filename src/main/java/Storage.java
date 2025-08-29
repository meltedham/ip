import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Storage {
    //for reading/writing a file
    private File file; //file object
    private String path;

    public Storage(String path)
    {
        this.path = path;
        this.file = new File(this.path);
    }

    public void createActualFile() {
        try {
            this.file.createNewFile();
        } catch (IOException e) {
            System.out.println("File could not be created");
        }
    }

    public void printFile() {
        try {
            Scanner scanner = new Scanner(this.file);
            int taskNum = 1;
            while (scanner.hasNext()) {
                System.out.println(taskNum + ". " + scanner.nextLine());
                taskNum++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found, creating it");
            this.createActualFile();
        }
    }

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

    public void modifyFile(Tasklist taskList) {
        this.clearFile();
        for (int i = 0; i < taskList.taskCount(); i++) {
            this.appendFile(taskList.getTask(i));
        }
    }

    public void clearFile() {
        try {
            FileWriter fileWriter = new FileWriter(this.path, false);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Couldn't clear file");
        }
    }

    public void fileToList(ArrayList<Task> list) {
        try {
            Path path = Paths.get(this.path);
            List<String> stringList = Files.readAllLines(path);
            for (int i = 0; i < stringList.size(); i++) {
                String line = stringList.get(i);
                String[] details = line.split("\\|");
                switch (details.length) {
                    case 3: //Todo
                        Todo todo = new Todo(details[2]);
                        if (details[1].equals("[X]")) {
                            todo.Mark();
                        }
                        list.add(todo);
                        break;
                    case 4: //Deadline
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                        LocalDateTime endDateTime = LocalDateTime.parse(details[3], formatter);
                        Deadline deadline = new Deadline(details[2], endDateTime);
                        if (details[1].equals("[X]")) {
                            deadline.Mark();
                        }
                        list.add(deadline);
                        break;
                    case 5: //Event
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                        LocalDateTime end = LocalDateTime.parse(details[3], format);
                        LocalDateTime start = LocalDateTime.parse(details[4], format);
                        Event event = new Event(details[2], end, start);
                        if (details[1].equals("[X]")) {
                            event.Mark();
                        }
                        list.add(event);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Couldn't read file");
        }
    }

}