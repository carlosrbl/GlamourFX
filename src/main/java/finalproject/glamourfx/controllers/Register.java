package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Register
{
    @FXML
    private void addUser(ActionEvent event)
    {
        try
        {
            String clientName = newUser();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalproject/glamourfx/customer.fxml"));
            Parent root = loader.load();

            CustomerInterface controller = loader.getController();
            controller.setClienteName(clientName);

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

    private String newUser()
    {
        Customer customer = new Customer("Sergio","a","a","a");
        return customer.getName();
    }

    @FXML
    private void cancel(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalproject/glamourfx/welcome.fxml"));
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
