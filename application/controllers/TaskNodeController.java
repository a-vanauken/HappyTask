package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


public class TaskNodeController implements Initializable {

    @FXML
    GridPane taskNode;

    @FXML
    Label titleLabel;

    @FXML
    Label descriptionLabel;

    @FXML
    Label dueDateLabel;

    @FXML
    Button optionsButton;

    public void setTitle(String title) {
        this.titleLabel.setText(title);
    }

    public void setDescription(String description) {
        descriptionLabel.setText(description);
    }

    public void setDueDate(String dueDate) {
        dueDateLabel.setText("Due: " + dueDate);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}