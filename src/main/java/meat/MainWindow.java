package meat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Meat meat;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/mlemcat.jpg"));
    private Image meatImage = new Image(this.getClass().getResourceAsStream("/images/mixue.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Meat instance */
    public void setMeat(Meat meat) {
        this.meat = meat;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Meat's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = meat.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMeatDialog(response, meatImage)
        );
        if (input.equals("bye")) {
            userInput.clear();
            this.closeWindow();
        }
        userInput.clear();
    }

    @FXML
    public void runMeat() {
        String greeting = meat.run();
        dialogContainer.getChildren().addAll(
                DialogBox.getMeatDialog(greeting, meatImage)
        );
        userInput.clear();
    }

    private void closeWindow() {
        Stage stage = (Stage) userInput.getScene().getWindow();
        stage.close();
    }
}

