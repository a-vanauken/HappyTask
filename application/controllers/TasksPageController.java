package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
