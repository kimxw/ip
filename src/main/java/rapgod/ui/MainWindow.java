package rapgod.ui;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import rapgod.bot.RapGod;
import rapgod.Main;


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

    private RapGod duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
                DialogBox.getInitialDialog(RapGod.getInitialMessage(), dukeImage)
        );
    }

    /** Injects the rapgod.Duke instance */
    public void setDuke(RapGod d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing rapgod.Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = duke.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();

        if (response.equals("Bye! Hope to see you again soon!")) {

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> closeCurrentStage());
            pause.play();
        }
    }

    private void closeCurrentStage() {
        Stage stage = (Stage) sendButton.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }
}
