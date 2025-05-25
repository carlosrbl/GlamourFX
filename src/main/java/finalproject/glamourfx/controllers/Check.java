package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Customer;
import finalproject.glamourfx.main.HelloApplication;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Check
{
    private static Check instance;

    @FXML
    private TextField txName;
    @FXML
    private PasswordField txPassword;
    @FXML
    private Label errorLabel;
    @FXML
    private Button yourButton;
    @FXML
    private Button yourButton2;

    public Check()
    {
        instance = this;
    }

    public static Check getInstance()
    {
        return instance;
    }

    @FXML
    private void checkUser(ActionEvent event)
    {
        boolean foundAdmin = checkAdmin();
        if (foundAdmin)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalproject/glamourfx/admin.fxml"));
                Parent root = loader.load();

                root.setRotationAxis( Rotate.Y_AXIS);
                root.setRotate(-90);

                AdminInterface controller = loader.getController();
                controller.setClienteName(txName.getText());

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
        else
        {
            List<Customer> customers = getCustomers();
            boolean found = check(customers);
            if (found)
            {
                try
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalproject/glamourfx/customer.fxml"));
                    Parent root = loader.load();

                    root.setRotationAxis( Rotate.Y_AXIS);
                    root.setRotate(-90);

                    CustomerInterface controller = loader.getController();
                    controller.setClienteName(txName.getText());

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
            else
            {
                Check.getInstance().setError("¡Incorrect data!");
            }
        }
    }

    private boolean checkAdmin()
    {
        return txName.getText().equalsIgnoreCase(HelloApplication.ADMIN.getName()) && txPassword.getText().equalsIgnoreCase(HelloApplication.ADMIN.getPassword());
    }

    private List<Customer> getCustomers()
    {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader inputFile = new BufferedReader(new FileReader(new File("customers.txt"))))
        {
            String line;
            while ((line = inputFile.readLine()) != null)
            {
                String[] data = line.split(";");
                customers.add(new Customer(data[0], data[1], data[2], data[3]));
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return customers;
    }

    private boolean check(List<Customer> customers)
    {
        boolean exists = false;
        for (Customer customer : customers)
        {
            if (customer.getName().equalsIgnoreCase(txName.getText()) && customer.getPassword().equalsIgnoreCase(txPassword.getText()) && !exists)
            {
                exists = true;
            }
        }
        return exists;
    }

    @FXML
    private void loadRegisterInterface(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalproject/glamourfx/register.fxml"));
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

    public void setError(String nombre)
    {
        errorLabel.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> errorLabel.setText(""));
        delay.play();
    }

    public void changeCursorToHand() {
        yourButton.setCursor(Cursor.HAND);
    }

    public void changeCursorToDefault() {
        yourButton.setCursor(Cursor.DEFAULT);
    }

    public void changeCursor2ToHand() {
        yourButton2.setCursor(Cursor.HAND);
    }

    public void changeCursor2ToDefault() {
        yourButton2.setCursor(Cursor.DEFAULT);
    }
}