package rapgod.ui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import rapgod.bot.RapGod;

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

    private RapGod rapGod;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image rapgodImage = new Image(this.getClass().getResourceAsStream("/images/DaRapgod.png"));

    /**
     * Initializes the controller for the dialog interface.
     * <p>
     * This method binds the vertical value property of the scroll pane to
     * the height property of the dialog container, ensuring that the scroll
     * pane scrolls automatically as new dialog boxes are added. It also
     * populates the dialog container with the initial dialog box containing
     * the initial message from the RapGod.
     * </p>
     *
     * @see DialogBox#getInitialDialog(String, Image)
     * @see RapGod#getInitialMessage()
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
                DialogBox.getInitialDialog(RapGod.getInitialMessage(), rapgodImage)
        );
    }

    /** Injects the rapgod.RapGod instance */
    public void setRapGod(RapGod d) {
        rapGod = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing rapgod.
     * RapGod's reply and then appends them to      * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = rapGod.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getRapgodDialog(response, rapgodImage)
        );
        userInput.clear();

        if (response.equals("Peace out! Catch you on the flip side!")) {

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
