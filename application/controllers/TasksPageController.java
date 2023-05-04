package application.controllers;

import application.Task;
import application.Task.*;
import application.TaskManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Node;


public class TasksPageController implements Initializable{

    @FXML 
    private GridPane tasksPane;

    @FXML
    private Pane taskHeaderPane;

    @FXML
    private Label newLabel;

    @FXML 
    private Label inprogressLabel;

    @FXML 
    private Label onholdLabel;

    @FXML 
    private Label doneLabel;

    @FXML
    private Button addButton;

    private Stage stage;

    @FXML
    private void addNewTask(ActionEvent event) throws Exception{
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        TaskDialogController dialog = new TaskDialogController(stage);
        dialog.editing = false;

        dialog.showAndWait().ifPresent(task -> {
            Node taskNode = createTaskNode(task);
            Column currentColumn = task.getColumn();
            addTaskToGrid(taskNode, currentColumn, currentColumn);
        });
    }

    private Node createTaskNode(Task task) {

        GridPane taskNode = new GridPane();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/TaskNode.fxml"));
            taskNode = loader.load();
            TaskNodeController controller = loader.getController();
            controller.parent = this;
            controller.taskId = task.id;
            controller.setTitle(task.getTitle());
            controller.setDescription(task.getDescription());
            controller.setDueDate(task.getDueDate());
            controller.setBorder(task);

        } catch(IOException e) {
            e.printStackTrace();
        }
        
        GridPane.setMargin(taskNode, new Insets(10, 5, 10, 5));
        return taskNode;
    }

    public void addTaskToGrid(Node taskNode, Column currentColumn, Column desiredColumn) {
        int startingRow = 2;
        int columnToPlace = getColumnIndex(desiredColumn);
        int maxRows = tasksPane.getRowConstraints().size();
        int emptyCell = -1;
        boolean empty = false;

        //Check each row in the specified column to find an empty cell 
        while(!empty) {
            for(int i = startingRow; i < maxRows; i++) {

                empty = findEmptyCell(tasksPane, i, columnToPlace);

                if(empty) {
                    emptyCell = i;
                    break;
                }
            }
        }     

        //If we are moving to a different column, first remove it from its current one
        if(getColumnIndex(currentColumn) != columnToPlace) {
            removeTaskFromGrid(taskNode);
        }

        //Then place in new column
        tasksPane.add(taskNode, columnToPlace, emptyCell);
    }

    public void removeTaskFromGrid(Node taskNode) {
        tasksPane.getChildren().remove(taskNode);
    }

    private int getColumnIndex(Column column) {
        if(column == Column.NEW) { 
            return 0;
        } else if(column == Column.IN_PROGRESS) {
            return 1;
        } else if(column == Column.ON_HOLD) {
            return 2;
        } else if(column == Column.DONE) {
            return 3;
        } else {
            return -1;
        }
    }

    private boolean findEmptyCell(GridPane gridPane, int rowToCheck, int columnToCheck) {
        /* Columns:
        0 = New
        1 = In progress
        2 = On hold
        3 = Done */

        for (Node node : gridPane.getChildren()) {
            
            if (GridPane.getColumnIndex(node) == columnToCheck && GridPane.getRowIndex(node) == rowToCheck) {
                return false;
            } 
        }
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(TaskManager.getNumberOfTasks() == 0) {
            TaskManager.init();
        }

        for(int i = 0; i < TaskManager.getNumberOfTasks(); i++) {
            Node taskNode = createTaskNode(TaskManager.getTaskAtIndex(i));
            addTaskToGrid(taskNode, TaskManager.getTaskAtIndex(i).getColumn(), TaskManager.getTaskAtIndex(i).getColumn());
        }  
    }
}
