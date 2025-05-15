package finalproject.glamourfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServicesInterface {
    public void back(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalproject/glamourfx/admin.fxml"));
            Parent root = loader.load();

            AdminInterface controller = loader.getController();
            controller.setClienteName("Admin");

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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
