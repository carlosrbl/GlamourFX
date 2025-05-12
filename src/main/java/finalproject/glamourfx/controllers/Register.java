package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Customer;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Register
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
    private Label nameLabel;
    @FXML
    private Label passwordLabel;

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
                if (!anyEmptyField()) {
                    newCustomer();
                    try {
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
            else
            {
                Register.getInstance().setErrorPassword("Passwords are different");
            }
        }
        else
        {
            Register.getInstance().setErrorName("This name exists");
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
                customers.add(new Customer(data[0], data[1], data[2], data[3]));
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

    public boolean anyEmptyField()
    {
        return txName.getText().isEmpty() || txPassword.getText().isEmpty() || txPassword2.getText().isEmpty() || txEmail.getText().isEmpty() || txPhoneNumber.getText().isEmpty() ;
    }

    public void setErrorName(String nombre)
    {
        nameLabel.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> nameLabel.setText(""));
        delay.play();
    }
    public void setErrorPassword(String nombre)
    {
        passwordLabel.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> passwordLabel.setText(""));
        delay.play();
    }
}
