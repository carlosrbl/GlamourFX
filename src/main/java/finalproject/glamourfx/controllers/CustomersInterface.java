package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Customer;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



import java.io.*;
import java.util.ArrayList;

public class CustomersInterface {
    @FXML
    private TextField CustomerPhoneNumber;
    @FXML
    private TextField CustomerEmail;
    @FXML
    private TextField CustomerPassword;
    @FXML
    private TextField CustomerName;
    @FXML
    private ListView<Customer> lvCustomers;

    String linea="";

    ArrayList<Customer> customers;

    String [] datos;

    private ArrayList<Customer> Lector()
    {
        try(BufferedReader bf = new BufferedReader(new FileReader("customers.txt")))
        {
            linea = bf.readLine();
            while (linea!=null)
            {
                datos = linea.split(";");
                customers.add(new Customer(datos[0],datos[1],datos[2],datos[3]));
                linea = bf.readLine();
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

        lvCustomers.setItems(datosObservables);
    }
}
