package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Scene sceneLandingPage, sceneDashboard;

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            //Need to set scene to landing page by default
            Parent root = FXMLLoader.load(getClass().getResource("fxml/LandingPage.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("HappyTask");
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}