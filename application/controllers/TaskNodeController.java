package application.controllers;

import application.TaskManager;
import application.Task.*;

import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;


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

    @FXML
    Button deleteButton;

    TasksPageController parent;

    Stage stage;

    int TaskId;


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

    public Column getState() {
        int col = GridPane.getColumnIndex(this.taskNode);
        
        switch(col) {
            case 0:
                return Column.TODO;
            case 1:
                return Column.IN_PROGRESS;
            case 2:
                return Column.ON_HOLD;
            case 3:
                return Column.DONE;
        }
        return null; 
    }

    public void setTitle(String title) {
        this.titleLabel.setText(title);
    }

    public void setDescription(String description) {
        descriptionLabel.setText(description);
    }

    public void setDueDate(String dueDate) {
        dueDateLabel.setText("Due: " + dueDate);
    }

    @FXML
    private void editTask(ActionEvent event) throws Exception {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        TaskDialogController dialog = new TaskDialogController(stage);
        dialog.editing = true;
        dialog.taskId = TaskId;
        Column oldState = getState();

        //Fill dialog with the task's existing values
        dialog.setTitleField(getTitle().getText());
        dialog.setDescriptionArea(getDescription().getText());
        dialog.setDueDate(getDueDate());
        dialog.setState(oldState);

        dialog.showAndWait().ifPresent(newTask -> {

            //Overwrite existing values with changes
            setTitle(newTask.getTitle());
            setDescription(newTask.getDescription());
            setDueDate(newTask.getDueDate());
            Column newState = newTask.getColumn();

            if(oldState != newState) {
                //If our state has changed, move the node on the grid
                parent.addTaskToGrid(taskNode, oldState, newState);
            }
        });
    }

    @FXML
    public void deleteTask() {
        //Remove from arraylist and file
        TaskManager.DeleteTask(TaskId);
        //Remove from grid
        parent.removeTaskFromGrid(taskNode);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}