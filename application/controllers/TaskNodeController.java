package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import application.models.CSVReadWrite;
import application.models.Task;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


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

    Stage stage;

    public void setTitle(String title) {
        this.titleLabel.setText(title);
    }

    public void setDescription(String description) {
        descriptionLabel.setText(description);
    }

    public void setDueDate(String dueDate) {
        dueDateLabel.setText("Due: " + dueDate);
    }

    public Label getTitle() {
        return this.titleLabel;
    }

    public Label getDescription() {
        return this.descriptionLabel;
    }

    public String getDueDate() {
        String dateOnly = (this.dueDateLabel.getText()).substring(5, dueDateLabel.getText().length());
        return dateOnly;
    }

    @FXML
    private void editTask(ActionEvent event) throws Exception{
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        TaskDialogController dialog = new TaskDialogController(stage);

        //Fill with the task's existing values
        dialog.setTitleField(getTitle().getText());
        dialog.setDescriptionArea(getDescription().getText());
        dialog.setDueDate(getDueDate());

        dialog.showAndWait().ifPresent(task -> {

            Task oldTask = new Task(this.getTitle().getText(), this.getDescription().getText(), this.getDueDate());
            //Overwrite existing values with changes
            setTitle(task.getTitle());
            setDescription(task.getDescription());
            setDueDate(task.getDueDate());

            //Update file to reflect changes
            try {
                final CSVReadWrite updator = new CSVReadWrite();
                updator.updateCSV(oldTask, task);
            } catch (IOException e){
                e.printStackTrace();
            }
           
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}