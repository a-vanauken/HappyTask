package application.controllers;

import application.Task;
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
import java.time.LocalDate;
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

    int taskId;


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

    public Column getColumn() {
        int col = GridPane.getColumnIndex(this.taskNode);

        switch(col) {
            case 0:
                return Column.NEW;
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

    public void setBorder(Task task) {
    
        LocalDate currentDate = LocalDate.now();
        LocalDate taskDate = TaskDialogController.unformatDate(task.getDueDate());

        taskNode.setStyle("-fx-border-style: solid; -fx-border-width: 3px; -fx-border-color: dimgray; -fx-border-radius: 10px; ");
        dueDateLabel.setStyle("-fx-text-fill: white;");
        int compare = taskDate.compareTo(currentDate);

        if (taskDate.compareTo(currentDate) < 7 && taskDate.compareTo(currentDate) >= 0 && task.getColumn() != Column.DONE) {
            //if the task due date is within one week
            taskNode.setStyle("-fx-border-style: solid; -fx-border-width: 3px; -fx-border-color: #FF7518;");
            dueDateLabel.setStyle("-fx-text-fill: #FF7518; -fx-font-weight: bold;");
        }
        if(taskDate.compareTo(currentDate) < 0 && task.getColumn() != Column.DONE) {
            //if the task due date has already passed
            taskNode.setStyle("-fx-border-style: solid; -fx-border-width: 3px; -fx-border-color: #D2042D;");
            dueDateLabel.setStyle("-fx-text-fill: #D2042D; -fx-font-weight: bold;");
        }      
    }

    @FXML
    private void editTask(ActionEvent event) throws Exception {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        TaskDialogController dialog = new TaskDialogController(stage);
        dialog.editing = true;
        dialog.taskId = taskId;
        Column oldState = getColumn();

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
            setBorder(newTask);

            if(oldState != newState) {
                //If our state has changed, move the node on the grid
                parent.addTaskToGrid(taskNode, oldState, newState);
            }
        });
    }

    @FXML
    private void deleteTask() {
        //Remove from arraylist and file
        TaskManager.deleteTask(taskId);
        //Remove from grid
        parent.removeTaskFromGrid(taskNode);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}