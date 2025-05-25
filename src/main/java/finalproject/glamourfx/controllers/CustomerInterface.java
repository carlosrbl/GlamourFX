package finalproject.glamourfx.controllers;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class CustomerInterface
{
    @FXML
    private Label helloLabel;
    @FXML
    private Button yourButton;

    @FXML
    private void logOut(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalproject/glamourfx/welcome.fxml"));
            Parent root = loader.load();

            root.setRotationAxis( Rotate.Y_AXIS);
            root.setRotate(-90);

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("GlamourFX");
            stage.setScene(scene);

            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");

            RotateTransition rotate = new RotateTransition(Duration.millis(700), root);
            rotate.setFromAngle(-90);
            rotate.setToAngle(0);

            stage.show();
            rotate.play();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadReservationInterface(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalproject/glamourfx/reservation.fxml"));
            Parent root = loader.load();

            root.setRotationAxis( Rotate.Y_AXIS);
            root.setRotate(-90);

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("GlamourFX");
            stage.setScene(scene);

            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");

            RotateTransition rotate = new RotateTransition(Duration.millis(700), root);
            rotate.setFromAngle(-90);
            rotate.setToAngle(0);

            stage.show();
            rotate.play();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadCustomerAppointmentsInterface(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalproject/glamourfx/customerAppointments.fxml"));
            Parent root = loader.load();

            root.setRotationAxis( Rotate.Y_AXIS);
            root.setRotate(-90);

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("GlamourFX");
            stage.setScene(scene);

            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");

            RotateTransition rotate = new RotateTransition(Duration.millis(700), root);
            rotate.setFromAngle(-90);
            rotate.setToAngle(0);

            stage.show();
            rotate.play();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setClienteName(String nombre)
    {
        helloLabel.setText("Hello, " + nombre.substring(0,1).toUpperCase() + nombre.substring(1) + "!");
    }

    public void changeCursorToHand() {
        yourButton.setCursor(Cursor.HAND);
    }

    public void changeCursorToDefault() {
        yourButton.setCursor(Cursor.DEFAULT);
    }
}
