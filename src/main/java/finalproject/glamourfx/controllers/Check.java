/**
 * @author Carlos
 * This class is responsible for checking the user login
 */

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
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Check implements ButtonCursor
{
    private static Check instance;

    @FXML
    private TextField txName;
    @FXML
    private PasswordField txPassword;
    @FXML
    private Label errorLabel;

    public Check()
    {
        instance = this;
    }

    /**
     * Gets the instance of the Check class.
     * @return The instance of the Check class.
     */
    public static Check getInstance()
    {
        return instance;
    }

    /**
     * Checks the user credentials and navigates to the appropriate interface.
     * @param event The ActionEvent triggered by the check user action.
     */
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
                System.out.println(e.getMessage());
            }
        }
        else
        {
            List<Customer> customers = getCustomers();
            boolean found = check(customers);
            if (found)
            {
                userLogged(customers);
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
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                Check.getInstance().setError("¡Incorrect data!");
            }
        }
    }

    /**
     * Checks if the user is an admin.
     * @return True if the user is an admin, false otherwise.
     */
    private boolean checkAdmin()
    {
        return txName.getText().equalsIgnoreCase(HelloApplication.ADMIN.getName()) && txPassword.getText().equalsIgnoreCase(HelloApplication.ADMIN.getPassword());
    }

    /**
     * Retrieves the list of customers from the file.
     * @return The list of customers.
     */
    private List<Customer> getCustomers()
    {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader inputFile = new BufferedReader(new FileReader("customers.txt")))
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

    /**
     * Checks if the user exists in the list of customers.
     * @param customers The list of customers.
     * @return True if the user exists, false otherwise.
     */
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

    /**
     * Sets the current customer in the session.
     * @param customers The list of customers.
     */
    private void userLogged(List<Customer> customers)
    {
        boolean ok = false;
        for (Customer customer : customers)
        {
            if (customer.getName().equalsIgnoreCase(txName.getText()) && !ok)
            {
                SessionManager.setCurrentCustomer(customer);
                ok = true;
            }
        }
    }

    /**
     * Loads the register interface.
     * @param event The ActionEvent triggered by the load register action.
     */
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
            System.out.println(e.getMessage());
        }
    }

    /**
     * Sets the error message.
     * @param nombre The error message.
     */
    public void setError(String nombre)
    {
        errorLabel.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(_ -> errorLabel.setText(""));
        delay.play();
    }

    /**
     * Changes the cursor to a hand cursor when hovering over a button.
     * @param event The MouseEvent triggered by hovering over a button.
     */
    public void changeCursorToHand(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setCursor(Cursor.HAND);
    }

    /**
     * Changes the cursor back to the default cursor when not hovering over a button.
     * @param event The MouseEvent triggered by not hovering over a button.
     */
    public void changeCursorToDefault(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setCursor(Cursor.DEFAULT);
    }
}