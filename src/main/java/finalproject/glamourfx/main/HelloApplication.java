package finalproject.glamourfx.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/finalproject/glamourfx/welcome.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("GlamourFX");
        primaryStage.setScene(scene);

        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");

        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}