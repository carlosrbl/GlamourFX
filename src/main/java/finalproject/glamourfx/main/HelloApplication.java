/**
 * @author Carlos
 * @author Adrián
 * @author Miguel
 * @author Nehuén
 * This is the main class that starts our application by loading the welcome interface
 */

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

import java.util.Objects;

/**
 * The main application class for GlamourFX.
 * This class extends {@link Application} and serves as the entry point for the JavaFX application.
 */
public class HelloApplication extends Application
{
    public static final Admin ADMIN = new Admin("Admin","1daw");

    /**
     * The entry point for the JavaFX application.
     * This method is called when the application is launched.
     * @param primaryStage the primary stage for this application, onto which the application scene can be set.
     * @throws Exception if there is an error loading the FXML resource.
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/finalproject/glamourfx/welcome.fxml")));

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

    /**
     * The main method for the application.
     * This method launches the JavaFX application.
     * @param args the command line arguments.
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}