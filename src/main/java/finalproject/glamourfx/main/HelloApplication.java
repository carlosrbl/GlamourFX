package finalproject.glamourfx.main;

import finalproject.glamourfx.data.Admin;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloApplication extends Application
{
    public static final Admin ADMIN = new Admin("Admin","1daw");
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/finalproject/glamourfx/welcome.fxml"));

        root.setRotationAxis(Rotate.Y_AXIS);
        root.setRotate(-90);

        Scene scene = new Scene(root);
        primaryStage.setTitle("GlamourFX");
        primaryStage.setScene(scene);

        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");

        RotateTransition rotate = new RotateTransition(Duration.millis(700), root);
        rotate.setFromAngle(-90);
        rotate.setToAngle(0);

        primaryStage.show();
        rotate.play();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}