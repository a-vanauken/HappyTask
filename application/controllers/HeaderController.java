package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;

public class HeaderController implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Text welcomeUserText;

    @FXML 
    private Button logoutButton;

    @FXML 
    private Text dateText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
}
