package application.controllers;

import application.Main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;

public class LandingPageController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Text welcomeText;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    // @FXML
    // private Button signupButton;

    Main parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    @FXML
    public void handleLoginButtonAction (ActionEvent event) throws Exception {

        // if(usernameField.getText() == "admin" && passwordField.getText() == "password") {
        //     loginMessageLabel.setText("Success!");
        //     loginMessageLabel.setStyle("-fx-text-fill: green;");
        
            root = FXMLLoader.load(getClass().getResource("/application/fxml/MainPage.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 1800, 850);
            scene.getStylesheets().add(getClass().getResource("/application/css/styles.css").toExternalForm());
            stage.setScene(scene);

            stage.setMaximized(true);
            stage.show();
        
        // } else {
        //     loginMessageLabel.setText("Incorrect username or password");
        //     loginMessageLabel.setStyle("-fx-text-fill: red;");
        // }        
    }
}
