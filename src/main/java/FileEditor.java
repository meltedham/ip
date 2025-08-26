import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileEditor {
    //for reading/writing a file
    private File file; //file object
    private String path;

    public FileEditor(String path)
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

    public void writeFile(String textToAdd) {
        try {
            FileWriter fileWriter = new FileWriter(this.path);
            fileWriter.write(textToAdd + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Couldn't add text to file: " + e.getMessage());
        }
    }

    public void appendFile(String textToAppend) {
        try {
            FileWriter fileWriter = new FileWriter(this.path, true); // create a FileWriter in append mode
            fileWriter.write(textToAppend + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Couldn't append text to file: " + e.getMessage());
        }
    }

    public void modifyFile(ArrayList<Task> list) {
        this.clearFile();
        for (int i = 0; i < list.size(); i++) {
            this.appendFile(list.get(i).toString());
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

}
