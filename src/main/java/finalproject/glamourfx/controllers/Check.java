package finalproject.glamourfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Check
{
    @FXML
    private void checkUser()
    {

    }

    @FXML
    private void loadRegisterInterface(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalproject/glamourfx/register.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("GlamourFX");
            stage.setScene(scene);

            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");

            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}