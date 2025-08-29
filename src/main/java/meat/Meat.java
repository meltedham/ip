package meat;

import meat.filestorage.Storage;
import meat.inputoutput.Parser;
import meat.inputoutput.Ui;
import meat.tasks.Task;
import meat.tasks.Tasklist;

import java.util.ArrayList;
import java.util.Scanner;

public class Meat {
    public static void main(String[] args) {
        Storage storage = new Storage("resources/meat.txt");

        ArrayList<Task> list = new ArrayList<>();
        Tasklist taskList = new Tasklist(list);
        Ui ui = new Ui(taskList);
        Parser parser = new Parser(ui, taskList, storage);
        //fileEditor.clearFile();
        ui.Start("Meat");

        storage.fileToList(list);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine(); //entire line of input
        while (!input.equals("bye")) {
            parser.checkAnyValid(input);
            input = scanner.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }
}