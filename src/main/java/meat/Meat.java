package meat;

import meat.filestorage.Storage;

import meat.inputoutput.Parser;
import meat.inputoutput.Ui;

import meat.tasks.Tasklist;

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
        this.taskList = new Tasklist();
        this.ui = new Ui(taskList);
        this.storage = new Storage("resources/meat.txt", ui);
        this.parser = new Parser(ui, taskList, storage);
        this.storage.fileToList(this.taskList);
    }

    public String run() {
        return storage.validateFileAccess();
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