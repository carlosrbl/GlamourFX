package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Customer;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomersInterface implements Initializable {
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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MostrarDatos();
    }

    private ArrayList<Customer> Lector()
    {
        String [] datos;
        ArrayList<Customer> customers = new ArrayList<Customer>();
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
    public void MostrarDatos()
    {
        ObservableList<Customer> datosObservables = FXCollections.observableArrayList(Lector());

        CustomersList.setItems(datosObservables);
    }
}
