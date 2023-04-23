package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Node;

public class TasksPageController implements Initializable{

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
    private void addTask(ActionEvent event) throws Exception{
        //Bring up the Add Task dialog
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        TaskDialogController dialog = new TaskDialogController(stage);
        dialog.showAndWait(); 
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
