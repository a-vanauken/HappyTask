package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.transform.Source;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Node;


public class MainPageController implements Initializable{
      
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button dashboardTab;

    @FXML
    private Button tasksTab;

    @FXML
    private Button accountTab;

    @FXML
    private BorderPane mainBorderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }

    @FXML
    public void switchToLandingPage(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/application/fxml/LandingPage.fxml"));
        //stage = (Stage) button.getScene().getWindow(); is the other way to do it
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/application/css/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleShowTasksPage(ActionEvent event) {
        loadFXML(getClass().getResource("/application/fxml/TasksPage.fxml"));
        mainBorderPane.getStylesheets().add(getClass().getResource("/application/css/styles.css").toExternalForm());
        tasksTab.setStyle("-fx-background-color: #EEEEEE; "); 
    }

    private void loadFXML(URL url) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            mainBorderPane.setCenter(loader.load());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
