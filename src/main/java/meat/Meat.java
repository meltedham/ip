package meat;

import meat.filestorage.Storage;

import meat.inputoutput.Parser;
import meat.inputoutput.Ui;

import meat.tasks.Task;
import meat.tasks.Tasklist;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class to run Meat.
 * Initializes storage, task list, UI, and parser, then
 * runs a loop until the user exits with "bye".
 */
public class Meat {

    private Storage storage;
    private Tasklist taskList;
    private Ui ui;
    private Parser parser;

    public Meat() {
        this.storage = new Storage("resources/meat.txt");
        this.taskList = new Tasklist();
        this.ui = new Ui(taskList);
        this.parser = new Parser(ui, taskList, storage);
        //fileEditor.clearFile();
        this.storage.fileToList(this.taskList);
    }

    public String run() {
        return ui.start("Meat");
        /*
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine(); //entire line of input
        while (!input.equals("bye")) {
            this.parser.checkAnyValid(input);
            input = scanner.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
        */
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        if (input.equals("bye")) {
            return "Bye. Hope to see you again soon!";
        } else {
            return this.parser.checkAnyValid(input);
        }
    }

    public static void main(String[] args) {
        new Meat();
    }
}