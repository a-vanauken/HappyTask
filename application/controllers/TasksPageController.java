package application.controllers;

import application.models.CSVReadWrite;
import application.models.Task;

import java.io.IOException;
import java.net.URL;
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
            writeTaskToFile(task);
        });
    }

    private Node createTaskNode(Task task) {

        GridPane taskNode = new GridPane();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/TaskNode.fxml"));
            taskNode = loader.load();
            TaskNodeController controller = loader.getController();

            controller.setTitle(task.getTitle());
            controller.setDescription(task.getDescription());
            controller.setDueDate(task.getDueDate());

        } catch(IOException e) {
            e.printStackTrace();
        }
        
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
    
    private boolean findEmptyCell(GridPane gridPane, int rowToCheck) {

        int column = 0; //this is the To Do column

        for (Node node : gridPane.getChildren()) {
            
            if (GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == rowToCheck) {
                return false;
            } 
        }
        return true;
    }  

    
    private void writeTaskToFile(Task newTask) {

        try {
            final CSVReadWrite writer = new CSVReadWrite();
            writer.writeToCSV(newTask);
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    private void showTaskFromFile(Task task) {
        Node taskNode = createTaskNode(task);
        addTaskToGrid(tasksPane, taskNode);
    }

    private void loadData() {
        String[][] data = new String[4][10];
        
        try {
            CSVReadWrite writer = new CSVReadWrite();
            data = writer.readFromCSV();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i = 0; i < data.length; i++) {
            int j = 0;

            if(data[i][j] != null) {
                showTaskFromFile(new Task(data[i][j], data[i][j+1], null, data[i][j+2]));
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }
}
