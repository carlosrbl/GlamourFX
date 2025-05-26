package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Customer;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;



import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomersInterface implements Initializable,ButtonCursor {
    @FXML
    private TextField CustomerPhoneNumber;
    @FXML
    private TextField CustomerEmail;
    @FXML
    private TextField CustomerPassword;
    @FXML
    private TextField CustomerName;
    @FXML
    private ListView<Customer> CustomersList;

    static ArrayList<Customer> customers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ShowInfo();
        CustomersList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            CustomerName.setText(newValue.getName());
            CustomerPassword.setText(newValue.getPassword());
            CustomerEmail.setText(newValue.getEmail());
            CustomerPhoneNumber.setText(newValue.getPhoneNumber());
        });
    }

    public ArrayList<Customer> Reader()
    {
        String [] datos;
        customers = new ArrayList<Customer>();
        String linea="";
        try(BufferedReader bf = new BufferedReader(new FileReader("customers.txt")))
        {

            while (( linea = bf.readLine())!=null)
            {
                datos = linea.split(";");
                customers.add(new Customer(datos[0],datos[1],datos[2],datos[3]));
            }
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        return customers;

    }
    public void ShowInfo()
    {
        ObservableList<Customer> datosObservables = FXCollections.observableArrayList(Reader());

        CustomersList.setItems(datosObservables);
    }

    public void update(ActionEvent actionEvent)
    {

        CustomersList.getSelectionModel().getSelectedItem().setName(CustomerName.getText());
        CustomersList.getSelectionModel().getSelectedItem().setPassword(CustomerPassword.getText());
        CustomersList.getSelectionModel().getSelectedItem().setEmail(CustomerEmail.getText());
        CustomersList.getSelectionModel().getSelectedItem().setPhoneNumber(CustomerPhoneNumber.getText());

        try
        {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("customers.txt")));

            for(Customer customer : CustomersList.getItems())
            {
                writer.println(customer.getName()+";"+customer.getPassword()+";"+customer.getEmail()+";"+customer.getPhoneNumber());
            }
            writer.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void delete(ActionEvent actionEvent)
    {
        try( PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("customers.txt"))))
        {
            customers.remove(CustomersList.getSelectionModel().getSelectedItem());
            CustomersList.setItems(FXCollections.observableArrayList(customers));
            for(Customer customer : customers)
            {
                writer.println(customer.getName()+";"+customer.getPassword()+";"+customer.getEmail()+";"+customer.getPhoneNumber());
            }

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private void back(ActionEvent actionEvent) {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalproject/glamourfx/admin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            AdminInterface controller = loader.getController();
            controller.setClienteName("Admin");

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
    @Override
    public void changeCursorToHand(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setCursor(Cursor.HAND);
    }
    @Override
    public void changeCursorToDefault(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setCursor(Cursor.DEFAULT);
    }
}
