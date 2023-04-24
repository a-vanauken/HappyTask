package application.controllers;

import application.models.Task;
import java.io.IOException;
import java.util.Objects;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class TaskDialogController extends Dialog<Task> {

    @FXML
    DialogPane pane;

    @FXML
    BorderPane container;
    
    @FXML
    Label titleLabel;
    
    @FXML
    TextField titleField;
    
    @FXML
    Label descriptionLabel;
    
    @FXML
    Label pathLabel;
    
    @FXML
    ComboBox<?> pathOptions;
    
    @FXML
    GridPane header;
    
    @FXML
    Label dateLabel;
    
    @FXML
    TextField dateField;
    
    @FXML
    Button saveButton;
    
    @FXML
    Button cancelButton;
    
    @FXML
    VBox footer;
    
    @FXML
    TextArea descriptionArea;

    @FXML
    ButtonType okButtonType;


    public TaskDialogController(Window owner) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/application/fxml/TaskDialog.fxml"));
            loader.setController(this);
            DialogPane dialogPane = loader.load();
            dialogPane.setContent(container);

            initOwner(owner);
            initModality(Modality.APPLICATION_MODAL);
            setResizable(true);
            setTitle("Add/Edit Task");
            setDialogPane(dialogPane);

            setResultConverter(buttonType -> {
                if(!Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData())) {
                    return null;
                }
                return new Task(titleField.getText(), descriptionArea.getText(), (String)pathOptions.getSelectionModel().getSelectedItem(), dateField.getText());
            });

            setOnShowing(dialogEvent -> Platform.runLater(() -> titleField.requestFocus()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}    