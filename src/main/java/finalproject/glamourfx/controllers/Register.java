/**
 * @author Carlos
 * This class contains the register interface controller
 */

package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Customer;
import javafx.animation.PauseTransition;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Register implements ButtonCursor
{
    private static Register instance;

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
    private Label errorLabel;

    public Register()
    {
        instance = this;
    }

    public static Register getInstance()
    {
        return instance;
    }

    @FXML
    private void addUser(ActionEvent event)
    {
        boolean passwordTrue = checkPassword();
        boolean customerExists = checkCustomer();
        if (!customerExists)
        {
            if (passwordTrue)
            {
                if (!anyEmptyField())
                {
                    newCustomer();
                    try {
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
                    } catch (IOException e) {
                        e.getMessage();
                    }
                }
                else
                {
                    Register.getInstance().setErrorName("¡No data introduced!");
                }
            }
            else
            {
                Register.getInstance().setErrorName("¡Passwords are different!");
            }
        }
        else
        {
            Register.getInstance().setErrorName("¡This name exists!");
        }
    }

    private boolean checkPassword()
    {
        return txPassword.getText().equals(txPassword2.getText());
    }

    private boolean checkCustomer() {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader inputFile = new BufferedReader(new FileReader(new File("customers.txt")))) {
            String line;
            while ((line = inputFile.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 4) {
                    customers.add(new Customer(data[0], data[1], data[2], data[3]));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        boolean customerExists = false;
        for (Customer customer : customers)
        {
            if (!customerExists)
            {
                customerExists = customer.getName().equalsIgnoreCase(txName.getText());
            }
        }
        return customerExists;
    }

    private void newCustomer()
    {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("customers.txt",true))))
        {
            pw.print(txName.getText() + ";" + txPassword.getText() + ";" + txEmail.getText() + ";" + txPhoneNumber.getText() + "\n");
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        Customer customer = new Customer(txName.getText(), txPassword.getText(), txEmail.getText(), txPhoneNumber.getText());
        SessionManager.setCurrentCustomer(customer);
    }

    @FXML
    private void cancel(ActionEvent event)
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

    public boolean anyEmptyField()
    {
        return txName.getText().isEmpty() || txPassword.getText().isEmpty() || txPassword2.getText().isEmpty() || txEmail.getText().isEmpty() || txPhoneNumber.getText().isEmpty() ;
    }

    public void setErrorName(String nombre)
    {
        errorLabel.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> errorLabel.setText(""));
        delay.play();
    }

    public void changeCursorToHand(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setCursor(Cursor.HAND);
    }

    public void changeCursorToDefault(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setCursor(Cursor.DEFAULT);
    }
}
