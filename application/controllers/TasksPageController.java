package application.controllers;

import application.models.Task;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Node;

public class TasksPageController implements Initializable{

    @FXML 
    private GridPane tasksPane;

    @FXML
    private Pane taskHeaderPane;

    @FXML
    private Label todoLabel;

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

        dialog.showAndWait().ifPresent(task -> {

            Node taskNode = createTaskNode(task);
            addTaskToGrid(tasksPane, taskNode);
        
        });
    }

    private Node createTaskNode(Task task) {
        Label titleLabel = new Label(task.getTitle());
        Label descriptionLabel = new Label(task.getDescription());
        Label dueDateLabel = new Label(task.getDueDate());
        titleLabel.setTextFill(Color.web("white"));
        dueDateLabel.setTextFill(Color.web("white"));
        descriptionLabel.setTextFill(Color.web("white"));

        VBox taskNode = new VBox();
        taskNode.getChildren().addAll(titleLabel, descriptionLabel, dueDateLabel);
        taskNode.setId("taskNodeContainer");
        GridPane.setMargin(taskNode, new Insets(10, 5, 10, 5));
        return taskNode;
    }

    private void addTaskToGrid(GridPane gridPane, Node taskNode) {
        int startingRow = 2;
        int maxRows = gridPane.getRowConstraints().size();
        int emptyCell = -1;
        boolean empty = false;
        
        //Check each row in the To Do column to find an empty cell 
        while(!empty) {
            for(int i = startingRow; i < maxRows; i++) {

                empty = findEmptyCell(gridPane, i);

                if(empty) {
                    emptyCell = i;
                    break;
                }
            }
        }     

        gridPane.add(taskNode, 0, emptyCell);
    }
    
    public boolean findEmptyCell(GridPane gridPane, int rowToCheck) {

        int column = 0; //this is the To Do column

        for (Node node : gridPane.getChildren()) {
            
            if (GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == rowToCheck) {
                return false;
            } 
        }
        return true;
    }  

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
