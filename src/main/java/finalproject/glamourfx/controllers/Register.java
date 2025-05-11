package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Register
{
    @FXML
    private TextField txName;
    @FXML
    private PasswordField txPassword;
    @FXML
    private PasswordField txPassword2;
    @FXML
    private TextField txEmail;
    @FXML
    private TextField txPhoneNumber;

    @FXML
    private void addUser(ActionEvent event)
    {
        boolean passwordTrue = checkPassword();
        // comprobar que el usuario no exista
        if (passwordTrue)
        {
            newCustomer();
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalproject/glamourfx/customer.fxml"));
                Parent root = loader.load();

                CustomerInterface controller = loader.getController();
                controller.setClienteName(txName.getText());

                Scene scene = new Scene(root);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("GlamourFX");
                stage.setScene(scene);

                stage.setFullScreen(true);
                stage.setFullScreenExitHint("");

                stage.show();
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    private boolean checkPassword()
    {
        return txPassword.getText().equals(txPassword2.getText());
    }

    private void newCustomer()
    {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("customers.txt",true))))
        {
            pw.println();
            pw.println(txName.getText() + ";" + txPassword.getText() + ";" + txEmail.getText() + ";" + txPhoneNumber.getText());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
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
